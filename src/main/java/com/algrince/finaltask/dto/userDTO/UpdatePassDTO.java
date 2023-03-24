package com.algrince.finaltask.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePassDTO {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty(message = "Old password should not be empty")
    private String password;

    @NotEmpty
    private String newPassword;
}
