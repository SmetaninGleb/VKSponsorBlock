package ru.vksponsorblock.VKSponsorBlock.security.services.Impl;

import org.springframework.stereotype.Service;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserCredentialsDto;
import ru.vksponsorblock.VKSponsorBlock.models.User;
import ru.vksponsorblock.VKSponsorBlock.repositories.UserRepository;
import ru.vksponsorblock.VKSponsorBlock.security.services.AuthValidateService;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserAlreadyExistsException;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserNotFoundException;

import java.util.Optional;

@Service
public class AuthValidateServiceImpl implements AuthValidateService {
    private final UserRepository userRepository;

    public AuthValidateServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validateRegister(UserCredentialsDto credentialDto) {
        Optional<User> userOpt = userRepository.findUserByUsername(credentialDto.getUsername());
        if (userOpt.isPresent()) {
            throw new UserAlreadyExistsException(credentialDto.getUsername());
        }
    }

    @Override
    public User validateAuth(UserCredentialsDto credentialDto) {
        Optional<User> userOpt = userRepository.findUserByUsername(credentialDto.getUsername());
        return userOpt.orElseThrow(() -> new UserNotFoundException(credentialDto.getUsername()));
    }
}
