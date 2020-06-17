package com.koulgar.Controller;

import com.koulgar.Model.UserDto;
import com.koulgar.Model.UserRegisterRequestModel;
import com.koulgar.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserByUserId(@PathVariable String userId) {
        /*
        TODO
         change endpoint to login if successful return userId
         use it as verification when getting notes
         */
        return userService.getUserByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody UserRegisterRequestModel userRegisterRequestModel) {
        /*
        TODO
         change endpoint to register
         */
        userService.saveUser(userRegisterRequestModel);
    }
}
