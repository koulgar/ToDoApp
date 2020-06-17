package com.koulgar.Converter;

import com.koulgar.Domain.User;
import com.koulgar.Model.UserRegisterRequestModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegisterUserConverter {

    public User convert(UserRegisterRequestModel userRegisterModel) {
        return User.builder()
                .username(userRegisterModel.getUsername())
                .password(userRegisterModel.getPassword())
                .notes(null)
                .createdDateTime(LocalDateTime.now())
                .build();
    }
}