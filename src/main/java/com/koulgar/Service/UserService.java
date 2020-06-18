package com.koulgar.Service;

import com.koulgar.Converter.GetUserResponseConverter;
import com.koulgar.Converter.RegisterUserConverter;
import com.koulgar.Domain.User;
import com.koulgar.Exception.PasswordDoesNotMatchException;
import com.koulgar.Exception.UserNotFoundException;
import com.koulgar.Model.UserDto;
import com.koulgar.Model.UserRegisterRequestModel;
import com.koulgar.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GetUserResponseConverter getUserResponseConverter;
    private final RegisterUserConverter registerUserConverter;

    public UserDto getUserByUserId(String userId) {
        /*
        TODO
         findByUsernameAndPassword and check if username already exists
         */
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı."));
        return getUserResponseConverter.convert(user);
    }

    public void saveUser(UserRegisterRequestModel userRegisterModel) {
        if (!Objects.equals(userRegisterModel.getPassword(), userRegisterModel.getPasswordConfirm())) {
            throw new PasswordDoesNotMatchException("Şifre eşleşmiyor.");
        }
        User userToRegister = registerUserConverter.convert(userRegisterModel);
        userRepository.save(userToRegister);
    }

}
