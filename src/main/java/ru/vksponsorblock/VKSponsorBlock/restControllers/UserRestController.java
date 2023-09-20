package ru.vksponsorblock.VKSponsorBlock.restControllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vksponsorblock.VKSponsorBlock.dto.RestErrorResponseDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserInfoDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentIdDto;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserService;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userInfo")
    public UserInfoDto userInfo(@RequestParam @Valid UserIdDto userIdDto) {
        return userService.getUserInfoById(userIdDto);
    }

    @PostMapping("/addSkippedVideoSegment")
    public ResponseEntity<HttpStatus> addSkippedVideoSegment(@RequestParam @Valid VideoSegmentIdDto segmentIdDto) {
        userService.addSkippedVideoSegment(segmentIdDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorResponseDto> UserNotFoundHandler(UserNotFoundException exception) {
        return new ResponseEntity<>(new RestErrorResponseDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
