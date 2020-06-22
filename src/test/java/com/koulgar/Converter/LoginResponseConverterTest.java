package com.koulgar.Converter;

import com.koulgar.Domain.User;
import com.koulgar.Model.UserDto;
import com.koulgar.Utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class LoginResponseConverterTest {

    @InjectMocks
    private LoginResponseConverter loginResponseConverter;

    @Test
    public void it_should_convert_login_response_to_userDto_object() {
        Clock.freeze();
        //given
        User user = User.builder()
                .id("123456")
                .username("koulgar")
                .password("123456")
                .notes(null)
                .createdDateTime(Clock.now())
                .build();

        //when
        UserDto userDto = loginResponseConverter.convert(user);

        //then
        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDto.getCreatedDateTime()).isEqualTo(user.getCreatedDateTime());
        assertThat(userDto.getNotes()).isEqualTo(user.getNotes());
        Clock.unfreeze();
    }
}