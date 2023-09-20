package ru.vksponsorblock.VKSponsorBlock.services.user.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserCredentialsDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserInfoDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserUsernameDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentIdDto;
import ru.vksponsorblock.VKSponsorBlock.models.EntityStatus;
import ru.vksponsorblock.VKSponsorBlock.models.User;
import ru.vksponsorblock.VKSponsorBlock.models.VideoSegment;
import ru.vksponsorblock.VKSponsorBlock.repositories.UserRepository;
import ru.vksponsorblock.VKSponsorBlock.repositories.VideoSegmentRepository;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserService;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserValidateService;
import ru.vksponsorblock.VKSponsorBlock.utils.RoleType;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserNotFoundException;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.VideoSegmentNotFoundException;

import java.util.Optional;
import java.util.UUID;

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
        infoDto.setSkippedTime(countAllSkippedTime(user));
        infoDto.setSavedTime(countAllSavedTime(user));
        return infoDto;
    }

    @Override
    public UserInfoDto getUserInfoByUsername(UserUsernameDto usernameDto) {
        User user = userValidateService.validateUserByUsername(usernameDto.getUsername());
        UserIdDto userIdDto = new UserIdDto();
        userIdDto.setUserId(user.getId());
        return getUserInfoById(userIdDto);
    }

    @Override
    public void addSkippedVideoSegment(VideoSegmentIdDto videoSegmentIdDto) {
        UUID segmentId = videoSegmentIdDto.getVideoSegmentId();
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = details.getUsername();
        Optional<User> optUser = userRepository.findUserByUsername(username);
        User user = optUser.orElseThrow(() -> new UserNotFoundException(username));
        Optional<VideoSegment> optSegment = segmentRepository.findVideoSegmentById(segmentId);
        VideoSegment segment = optSegment.orElseThrow(() -> new VideoSegmentNotFoundException(segmentId));
        user.getSkippedSegments().add(segment);
        userRepository.save(user);
    }

    private Float countAllSegmentsTime(User user) {
        return segmentRepository
                .findAllByCreator(user)
                .stream()
                .map(segment -> segment.getEndTimeSeconds() - segment.getStartTimeSeconds())
                .reduce(Float::sum)
                .orElse(0f);
    }

    private Float countAllSkippedTime(User user) {
        return user.getSkippedSegments()
                .stream()
                .map(segment -> segment.getEndTimeSeconds() - segment.getStartTimeSeconds())
                .reduce(Float::sum)
                .orElse(0f);
    }

    private Float countAllSavedTime(User user) {
        return user.getSkippedSegments()
                .stream()
                .map(segment -> {
                    Float time = segment.getEndTimeSeconds() - segment.getStartTimeSeconds();
                    time *= segment.getSkippedUsers().size();
                    return time;
                })
                .reduce(Float::sum)
                .orElse(0f);
    }
}
