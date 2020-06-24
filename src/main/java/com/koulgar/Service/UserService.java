package com.koulgar.Service;

import com.koulgar.Converter.LoginResponseConverter;
import com.koulgar.Converter.RegisterUserConverter;
import com.koulgar.Domain.User;
import com.koulgar.Exception.PasswordDoesNotMatchException;
import com.koulgar.Exception.UserAlreadyExistsException;
import com.koulgar.Exception.UserNotFoundException;
import com.koulgar.Model.User.UserLoginRequest;
import com.koulgar.Model.User.UserDto;
import com.koulgar.Model.User.UserRegisterRequest;
import com.koulgar.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginResponseConverter loginResponseConverter;
    private final RegisterUserConverter registerUserConverter;

    public UserDto userLogin(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword())
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı adi ya da sifre yanlis."));
        return loginResponseConverter.convert(user);
    }

    public void register(UserRegisterRequest userRegisterRequest) {
        validatePassword(userRegisterRequest.getPassword(), userRegisterRequest.getPasswordConfirm());
        validateUsername(userRegisterRequest.getUsername());
        User userToRegister = registerUserConverter.convert(userRegisterRequest);
        userRepository.save(userToRegister);
    }

    private void validateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("Bu kullanici adi zaten alinmis.");
        }
    }

    private void validatePassword(String password, String passwordConfirm) {
        if (!Objects.equals(password, passwordConfirm)) {
            throw new PasswordDoesNotMatchException("Şifre eşleşmiyor.");
        }
    }

}
