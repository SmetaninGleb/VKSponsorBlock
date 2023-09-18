package ru.vksponsorblock.VKSponsorBlock.restControllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserInfoDto;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserService;

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
}
