package com.example.Gruppuppgift_Statsbibloteket.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.repository.AdminsRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private AdminsRepository adminsRepository;
    private UserRepository userRepository;

    public UserDetailServiceImpl(AdminsRepository adminsRepository, UserRepository userRepository) {
        this.adminsRepository = adminsRepository;
        this.userRepository = userRepository;
    }

    /*@Override
    public UserDetails loadAdminByUsername(String username) throws UsernameNotFoundException {
        Admins admin = adminsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found!"));

        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(),
                new ArrayList<>());

    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admins> admin = adminsRepository.findByUsername(username);
        if (admin.isPresent()) {
            Admins admins = admin.get();
            return new org.springframework.security.core.userdetails.User(admins.getUsername(),admins.getPassword(), new ArrayList<>());
        }

        //hämtar ett user object baserat på username
        Users user= userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        //skapar och returnerar ett userDetails objekt baserat på ett User objekt hämtat från databasen
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

}
