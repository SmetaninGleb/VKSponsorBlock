package ru.vksponsorblock.VKSponsorBlock.services.user;

import ru.vksponsorblock.VKSponsorBlock.dto.user.UserCredentialsDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserInfoDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserUsernameDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentIdDto;
import ru.vksponsorblock.VKSponsorBlock.models.User;

public interface UserService {
    User register(UserCredentialsDto userCredentialsDto);
    UserUsernameDto getUsernameById(UserIdDto userIdDto);
    UserIdDto getUserIdByUsername(UserUsernameDto usernameDto);
    UserInfoDto getUserInfoById(UserIdDto userIdDto);
    void addSkippedVideoSegment(VideoSegmentIdDto videoSegmentIdDto);
}
