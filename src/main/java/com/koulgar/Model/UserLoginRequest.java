package com.koulgar.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

    @Size(min=2, max = 15, message = "user.username.length.not.valid")
    @NotBlank(message = "user.username.not.null")
    private String username;

    @Size(min=2, max = 15, message = "user.password.length.not.valid")
    @NotBlank(message = "user.password.not.null")
    private String password;
}
