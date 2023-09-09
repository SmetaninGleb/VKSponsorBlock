package ru.vksponsorblock.VKSponsorBlock.services.user;

import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserUsernameDto;
import ru.vksponsorblock.VKSponsorBlock.models.User;

public interface UserService {
    User register(User user);
    UserUsernameDto getUsernameById(UserIdDto userIdDto);
    UserIdDto getUserIdByUsername(UserUsernameDto usernameDto);

}
