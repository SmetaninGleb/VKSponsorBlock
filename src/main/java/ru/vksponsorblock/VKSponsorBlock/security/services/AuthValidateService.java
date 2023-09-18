package ru.vksponsorblock.VKSponsorBlock.security.services;

import ru.vksponsorblock.VKSponsorBlock.dto.user.UserCredentialsDto;
import ru.vksponsorblock.VKSponsorBlock.models.User;

public interface AuthValidateService {
    void validateRegister(UserCredentialsDto credentialDto);
    User validateAuth(UserCredentialsDto credentialDto);
}
