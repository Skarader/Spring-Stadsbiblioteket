package com.example.Gruppuppgift_Statsbibloteket;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//detta är vårt egna filter för att hantera JWT autentisering
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtRequestFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = null;
        String username = null;

        //vi vill plocka ut token från en requests Authorization header
        final String authorizationHeader = request.getHeader("Authorization");

        //kollar om headern inte är tom och är skriven på formatet "Bearer <token>"
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            jwt = authorizationHeader.substring(7); //plockar ut token efter "Bearer "
            System.out.println("jwt: " + jwt);
            username = jwtUtil.extractUserName(jwt);    //vi extraherar användarnamnet från token
        }


        //SecurityContext används av Spring Security för att lagra information om den aktuella användaren och deras autentiseringstillstånd.
        // här kollar vi så att det inte redan finns en auktorisering i kontexten.
        // Detta säkerställer att autentiseringen inte ställs in två gånger för samma request.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //vi laddar in användardata från databasen med hjälp av användarnamnet från vår token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            //kontroller om token är giltig, signaturen korrekt och namn matchar användaren som laddats in
            if(jwtUtil.isValid(jwt,username)){

                //en token som spring security använder för att representera en autentiserad användare
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());


                //Lägger autentiseringstoken i Spring Securitys securityContext.
                // Detta gör användaren autentiserad för resten av requesten.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request, response);


    }
}
