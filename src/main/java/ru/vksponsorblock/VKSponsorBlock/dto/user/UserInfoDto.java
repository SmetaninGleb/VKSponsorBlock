package ru.vksponsorblock.VKSponsorBlock.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserInfoDto {
    private UUID userId;
    private String username;
    private Integer segmentNumber;
    private Float allSegmentsTime;
}
