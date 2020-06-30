package com.koulgar.Converter;

import com.koulgar.Domain.User;
import com.koulgar.Model.User.UserRegisterRequest;
import com.koulgar.Utils.Clock;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserConverter {

    public User convert(UserRegisterRequest userRegisterModel) {
        return User.builder()
                .username(userRegisterModel.getUsername())
                .password(userRegisterModel.getPassword())
                .createdDateTime(Clock.now())
                .build();
    }

}
