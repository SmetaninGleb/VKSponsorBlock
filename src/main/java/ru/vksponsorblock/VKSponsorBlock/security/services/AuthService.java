package ru.vksponsorblock.VKSponsorBlock.security.services;

import ru.vksponsorblock.VKSponsorBlock.dto.user.UserCredentialsDto;
import ru.vksponsorblock.VKSponsorBlock.security.dto.JwtDto;

public interface AuthService {
    JwtDto register(UserCredentialsDto userCredentialsDto);
    JwtDto authorize(UserCredentialsDto userCredentialsDto);
}
