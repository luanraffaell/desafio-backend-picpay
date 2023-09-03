package com.picpaysimplificado2.dtos;

import com.picpaysimplificado2.domain.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDTO {
    @NotBlank
    private String fullName;
    @NotBlank
    private String SSN;
    @NotBlank
    @Email
    private String email;
   @PositiveOrZero
    private BigDecimal balance;
   @NotBlank
    private String password;

    private UserType userType;
}
