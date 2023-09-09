package ru.vksponsorblock.VKSponsorBlock.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserIdDto {
    @NotNull
    private UUID userId;
}
