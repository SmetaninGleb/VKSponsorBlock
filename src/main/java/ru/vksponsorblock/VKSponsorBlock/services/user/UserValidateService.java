package ru.vksponsorblock.VKSponsorBlock.services.user;

import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.models.User;

import java.util.UUID;

public interface UserValidateService {
    User validateUserByUsername(String username);
    User validateUserById(UUID userId);
}
