package com.wheon.zulzulserver.controller;

import com.wheon.zulzulserver.dto.request.AuthRequestDto;
import com.wheon.zulzulserver.entity.UserEntity;
import com.wheon.zulzulserver.jwt.JwtUtil;
import com.wheon.zulzulserver.repository.UserRepository;
import com.wheon.zulzulserver.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDto request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        Cookie accessCookie = new Cookie("Authorization", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60 * 60); // 1시간
        accessCookie.setAttribute("SameSite", "None");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("Refresh-Token", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일
        refreshCookie.setAttribute("SameSite", "None");
        response.addCookie(refreshCookie);

        return ResponseEntity.ok("");
    }

    @PostMapping("/register")
    public UserEntity register(@RequestBody AuthRequestDto request) {
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return userService.register(user);
    }

    @PostMapping("/refresh")
    public String refreshAccessToken(@RequestBody String refreshToken) {
        System.out.println(refreshToken);
        String newAccessToken = jwtUtil.refreshAccessToken(refreshToken);
        if (newAccessToken != null) return newAccessToken;
        throw new RuntimeException("Invalid refresh token");
    }

}

