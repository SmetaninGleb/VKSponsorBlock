package ru.vksponsorblock.VKSponsorBlock.restControllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserCredentialsDto;
import ru.vksponsorblock.VKSponsorBlock.security.dto.JwtDto;
import ru.vksponsorblock.VKSponsorBlock.security.services.AuthService;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthRestController {

    private final AuthService authService;

    @Autowired
    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public JwtDto register(@RequestBody @Valid UserCredentialsDto userCredentialsDto) {
        log.debug("Username {}, password {}", userCredentialsDto.getUsername(), userCredentialsDto.getPassword());
        return authService.register(userCredentialsDto);
    }

    @PostMapping("/auth")
    public JwtDto auth(@RequestBody @Valid UserCredentialsDto userCredentialsDto) {
        return authService.authorize(userCredentialsDto);
    }
}
