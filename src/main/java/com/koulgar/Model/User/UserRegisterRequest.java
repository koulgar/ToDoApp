package com.koulgar.Model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @Size(min=2, max = 15, message = "user.username.length.not.valid")
    @NotBlank(message = "user.username.not.null")
    private String username;

    @Size(min=2, max = 15, message = "user.password.length.not.valid")
    @NotBlank(message = "user.password.not.null")
    private String password;

    @Size(min=2, max = 15, message = "user.password.confirm.length.not.valid")
    @NotBlank(message = "user.password.confirm.not.null")
    private String passwordConfirm;
}
