package com.payupi.user.service.impl;

import com.payupi.user.entity.User;
import com.payupi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

     private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         User user = userRepository.findByEmail(username)
                 .orElseThrow( () -> new UsernameNotFoundException("user not found"));




        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
//                .password(user.getPassword())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(role -> "ROLE_" + role)
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                )
                .build();
    }
}
