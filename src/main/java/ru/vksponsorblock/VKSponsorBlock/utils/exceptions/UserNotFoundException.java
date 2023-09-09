package ru.vksponsorblock.VKSponsorBlock.utils.exceptions;

import lombok.Data;

import java.util.UUID;

@Data
public class UserNotFoundException extends RuntimeException {

    private String username;
    private UUID userId;
    private String message;

    public UserNotFoundException() {}

    public void setUsername(String username) {
        this.username = username;
        message = "User with username " + username + " not found!";
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
        message = "User with id " + userId + " not found!";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
