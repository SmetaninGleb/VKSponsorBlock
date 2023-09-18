package ru.vksponsorblock.VKSponsorBlock.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCredentialsDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
