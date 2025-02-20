package com.wheon.zulzulserver.service;

import com.wheon.zulzulserver.entity.UserEntity;
import com.wheon.zulzulserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<String> register(UserEntity user) {
        Optional<UserEntity> findUser = userRepository.findByUsername(user.getUsername());

        if (findUser.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");

        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role("ROLE_USER")
                .build();

        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Register user success");
    }
}
