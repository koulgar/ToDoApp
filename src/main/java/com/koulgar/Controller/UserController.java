package com.koulgar.Controller;

import com.koulgar.Model.Note.NoteDto;
import com.koulgar.Model.User.UserDto;
import com.koulgar.Model.User.UserLoginRequest;
import com.koulgar.Model.User.UserRegisterRequest;
import com.koulgar.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
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

    @GetMapping("user/notes/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteDto> getUserNotes(@PathVariable String userId) {
        return userService.getUserNotes(userId);
    }
}
