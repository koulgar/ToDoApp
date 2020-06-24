package com.koulgar.Converter;

import com.koulgar.Domain.User;
import com.koulgar.Model.User.UserDto;
import org.springframework.stereotype.Component;

@Component
public class LoginResponseConverter {

        public UserDto convert(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .notes(user.getNotes())
                .createdDateTime(user.getCreatedDateTime())
                .build();
    }

}
