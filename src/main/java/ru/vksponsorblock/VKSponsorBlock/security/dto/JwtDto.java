package ru.vksponsorblock.VKSponsorBlock.security.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtDto {
    private UUID userId;
    private String jwtToken;
}
