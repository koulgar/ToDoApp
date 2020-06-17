package com.koulgar.Converter;

import com.koulgar.Domain.User;
import com.koulgar.Model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class GetUserResponseConverter {

    public UserDto convert(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .notes(user.getNotes())
                .createdDateTime(user.getCreatedDateTime())
                .build();
    }
}
