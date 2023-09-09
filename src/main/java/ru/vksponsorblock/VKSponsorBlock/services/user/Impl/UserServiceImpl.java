package ru.vksponsorblock.VKSponsorBlock.services.user.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.user.UserUsernameDto;
import ru.vksponsorblock.VKSponsorBlock.models.EntityStatus;
import ru.vksponsorblock.VKSponsorBlock.models.User;
import ru.vksponsorblock.VKSponsorBlock.models.Role;
import ru.vksponsorblock.VKSponsorBlock.repositories.UserRepository;
import ru.vksponsorblock.VKSponsorBlock.repositories.RoleRepository;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserService;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserValidateService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidateService userValidateService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserValidateService userValidateService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidateService = userValidateService;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findRoleByName("ROLE_USER");
        List<Role> memberRoles = new ArrayList<>();
        memberRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(memberRoles);
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

}
