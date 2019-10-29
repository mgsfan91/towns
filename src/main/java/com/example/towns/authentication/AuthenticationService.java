package com.example.towns.authentication;

import com.example.towns.user.User;
import com.example.towns.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;


    public TokenResponse authenticate(Credentials credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()));
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(credentials.getUsername());
        String token = tokenUtil.generateOneDayToken(userDetails);
        return new TokenResponse(token);
    }

    public TokenResponse register(Credentials credentials) {
        String password = passwordEncoder.encode(credentials.getPassword());
        User newUser = new User(credentials.getUsername(), password, null);
        userRepository.save(newUser);
        return authenticate(credentials);
    }
}
