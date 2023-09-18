package ru.vksponsorblock.VKSponsorBlock.services.user.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserCredentialsDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserInfoDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserUsernameDto;
import ru.vksponsorblock.VKSponsorBlock.models.EntityStatus;
import ru.vksponsorblock.VKSponsorBlock.models.User;
import ru.vksponsorblock.VKSponsorBlock.repositories.UserRepository;
import ru.vksponsorblock.VKSponsorBlock.repositories.VideoSegmentRepository;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserService;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserValidateService;
import ru.vksponsorblock.VKSponsorBlock.utils.RoleType;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final VideoSegmentRepository segmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidateService userValidateService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           VideoSegmentRepository segmentRepository,
                           PasswordEncoder passwordEncoder,
                           UserValidateService userValidateService) {
        this.userRepository = userRepository;
        this.segmentRepository = segmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidateService = userValidateService;
    }

    @Override
    public User register(UserCredentialsDto userCredentialsDto) {
        User user = new User();

        user.setUsername(userCredentialsDto.getUsername());
        user.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword()));
        user.setRole(RoleType.USER);
        user.setStatus(EntityStatus.ACTIVE);

        User registeredUser = userRepository.save(user);

        if (log.isInfoEnabled()) {
            log.info("User {} has been registered", user.getUsername());
        }
        return registeredUser;
    }

    @Override
    public UserUsernameDto getUsernameById(UserIdDto userIdDto) {
        User user = userValidateService.validateUserById(userIdDto.getUserId());
        UserUsernameDto usernameDto = new UserUsernameDto();
        usernameDto.setUsername(user.getUsername());
        return usernameDto;
    }

    @Override
    public UserIdDto getUserIdByUsername(UserUsernameDto usernameDto) {
        User user = userValidateService.validateUserByUsername(usernameDto.getUsername());
        UserIdDto idDto = new UserIdDto();
        idDto.setUserId(user.getId());
        return idDto;
    }

    @Override
    public UserInfoDto getUserInfoById(UserIdDto userIdDto) {
        User user = userValidateService.validateUserById(userIdDto.getUserId());
        UserInfoDto infoDto = new UserInfoDto();
        infoDto.setUserId(user.getId());
        infoDto.setUsername(user.getUsername());
        infoDto.setSegmentNumber(user.getCreatedSegments().size());
        infoDto.setAllSegmentsTime(countAllSegmentsTime(user));
        return infoDto;
    }

    private Float countAllSegmentsTime(User user) {
        return segmentRepository
                .findAllByCreator(user)
                .stream()
                .map(segment -> segment.getEndTimeSeconds() - segment.getStartTimeSeconds())
                .reduce(Float::sum)
                .orElseThrow(() -> new RuntimeException("Cannot count all segments time!"));
    }

}
