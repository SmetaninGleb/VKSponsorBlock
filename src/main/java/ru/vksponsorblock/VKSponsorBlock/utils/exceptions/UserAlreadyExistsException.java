package ru.vksponsorblock.VKSponsorBlock.utils.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    private final String username;

    public UserAlreadyExistsException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "User with username '" + username + "' already exists";
    }
}
