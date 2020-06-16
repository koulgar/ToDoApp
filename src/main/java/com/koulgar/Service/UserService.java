package com.koulgar.Service;

import com.koulgar.Domain.User;
import com.koulgar.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public Optional<User> getUser(String userId) {
        return userRepository.findById(userId);
    }

    public void saveUser(User user) {
        user.setCreatedDateTime(Date.from(Instant.now()));
        userRepository.save(user);
    }

}
