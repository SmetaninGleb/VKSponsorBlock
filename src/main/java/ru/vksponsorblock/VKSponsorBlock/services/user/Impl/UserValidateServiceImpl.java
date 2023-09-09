package ru.vksponsorblock.VKSponsorBlock.services.user.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.models.User;
import ru.vksponsorblock.VKSponsorBlock.repositories.UserRepository;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserValidateService;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserValidateServiceImpl implements UserValidateService {
    private final UserRepository userRepository;

    @Autowired
    public UserValidateServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User validateUserByUsername(String username) {
        Optional<User> optUser = userRepository.findUserByUsername(username);
        return optUser.orElseThrow(() -> getExceptionByUsername(username));
    }

    @Override
    public User validateUserById(UUID userId) {
        Optional<User> optUser = userRepository.findById(userId);
        return optUser.orElseThrow(() -> getExceptionById(userId));
    }

    private UserNotFoundException getExceptionById(UUID userId) {
        UserNotFoundException exception = new UserNotFoundException();
        exception.setUserId(userId);
        return exception;
    }

    private UserNotFoundException getExceptionByUsername(String username) {
        UserNotFoundException exception = new UserNotFoundException();
        exception.setUsername(username);
        return exception;
    }
}
