package com.kamthan.InventoryPro.service.security;

import com.kamthan.InventoryPro.dto.security.LoginRequestDTO;
import com.kamthan.InventoryPro.dto.security.LoginResponseDTO;
import com.kamthan.InventoryPro.model.User;
import com.kamthan.InventoryPro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public LoginResponseDTO login(
            LoginRequestDTO request) {

        log.info(
                "Login attempt | username={}",
                request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(
                                "ROLE_" + user.getRole().name()
                        )
                        .build()
        );

        log.info(
                "Login successful | username={}",
                user.getUsername());

        return new LoginResponseDTO(token);
    }
}
