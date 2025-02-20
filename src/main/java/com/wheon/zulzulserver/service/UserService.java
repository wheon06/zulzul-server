package com.wheon.zulzulserver.service;

import com.wheon.zulzulserver.entity.UserEntity;
import com.wheon.zulzulserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity register(UserEntity user) {
        Optional<UserEntity> findUser = userRepository.findByUsername(user.getUsername());

        if (findUser.isPresent()) throw new RuntimeException("User already exists");

        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role("ROLE_USER")
                .build();

        return userRepository.save(newUser);
    }
}
