package ru.vksponsorblock.VKSponsorBlock.dto;

import lombok.Data;

@Data
public class RestErrorResponseDto {

    private String message;

    public RestErrorResponseDto(String message) {
        this.message = message;
    }
}
