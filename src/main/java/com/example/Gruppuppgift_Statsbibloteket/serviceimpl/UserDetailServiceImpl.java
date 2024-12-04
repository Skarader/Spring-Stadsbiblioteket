package com.example.Gruppuppgift_Statsbibloteket.serviceimpl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.repository.AdminsRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private AdminsRepository adminsRepository;

    public UserDetailServiceImpl(AdminsRepository adminsRepository) {
        this.adminsRepository = adminsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admins admin = adminsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found!"));

        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(),
                new ArrayList<>());

    }

}
