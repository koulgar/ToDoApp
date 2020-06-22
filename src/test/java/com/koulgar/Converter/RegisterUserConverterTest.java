package com.koulgar.Converter;

import com.koulgar.Domain.User;
import com.koulgar.Model.UserRegisterRequest;
import com.koulgar.Utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class RegisterUserConverterTest {

    @InjectMocks
    private RegisterUserConverter registerUserConverter;

    @Test
    public void it_should_convert_register_request_to_user_object() {
        Clock.freeze();
        //given
        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .username("koulgar")
                .password("123456")
                .passwordConfirm("123456")
                .build();

        //when
        User user = registerUserConverter.convert(registerRequest);

        //then
        assertThat(user.getUsername()).isEqualTo("koulgar");
        assertThat(user.getPassword()).isEqualTo("123456");
        assertThat(user.getNotes()).isNull();
        assertThat(user.getCreatedDateTime()).isEqualTo(Clock.now());
        Clock.unfreeze();
    }
}