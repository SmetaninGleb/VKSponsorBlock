package ru.vksponsorblock.VKSponsorBlock.security.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserCredentialsDto;
import ru.vksponsorblock.VKSponsorBlock.models.User;
import ru.vksponsorblock.VKSponsorBlock.security.dto.JwtDto;
import ru.vksponsorblock.VKSponsorBlock.security.services.AuthService;
import ru.vksponsorblock.VKSponsorBlock.security.services.AuthValidateService;
import ru.vksponsorblock.VKSponsorBlock.security.utils.JwtUtil;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthValidateService authValidateService;

    @Autowired
    public AuthServiceImpl(JwtUtil jwtUtil,
                           UserService userService,
                           AuthValidateService authValidateService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authValidateService = authValidateService;
    }

    @Transactional
    @Override
    public JwtDto register(UserCredentialsDto userCredentialsDto) {
        authValidateService.validateRegister(userCredentialsDto);
        User user = userService.register(userCredentialsDto);
        String token = jwtUtil.generateToken(user);
        JwtDto dto = new JwtDto();
        dto.setUserId(user.getId());
        dto.setJwtToken(token);
        return dto;
    }

    @Transactional
    @Override
    public JwtDto authorize(UserCredentialsDto userCredentialsDto) {
        User user = authValidateService.validateAuth(userCredentialsDto);
        String token = jwtUtil.generateToken(user);
        JwtDto dto = new JwtDto();
        dto.setUserId(user.getId());
        dto.setJwtToken(token);
        return dto;
    }
}
