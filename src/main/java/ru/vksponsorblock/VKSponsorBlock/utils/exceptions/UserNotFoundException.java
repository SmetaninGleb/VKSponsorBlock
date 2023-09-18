package ru.vksponsorblock.VKSponsorBlock.utils.exceptions;

import lombok.Data;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    private String username;
    private UUID userId;
    private String message;

    public UserNotFoundException(String username) {
        this.username = username;
        message = "User with username " + username + " not found!";
    }

    public UserNotFoundException(UUID userId) {
        this.userId = userId;
        message = "User with id " + userId + " not found!";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
