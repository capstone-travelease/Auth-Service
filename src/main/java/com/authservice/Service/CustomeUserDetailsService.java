package com.authservice.Service;

import com.authservice.Entity.CustomUserDetails;
import com.authservice.Entity.Users;
import com.authservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> credentail = userRepository.findByEmail(username);
        return credentail.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("NOT FOUND"));
    }
}
