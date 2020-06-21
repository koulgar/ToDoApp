package com.koulgar.Controller;

import com.koulgar.Model.UserLoginRequest;
import com.koulgar.Model.UserDto;
import com.koulgar.Model.UserRegisterRequest;
import com.koulgar.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserDto userLogin(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return userService.userLogin(userLoginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void userRegister(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        userService.register(userRegisterRequest);
    }
}
