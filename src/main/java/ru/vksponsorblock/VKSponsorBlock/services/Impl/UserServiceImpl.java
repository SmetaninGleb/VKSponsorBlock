package ru.vksponsorblock.VKSponsorBlock.services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vksponsorblock.VKSponsorBlock.models.EntityStatus;
import ru.vksponsorblock.VKSponsorBlock.models.User;
import ru.vksponsorblock.VKSponsorBlock.models.Role;
import ru.vksponsorblock.VKSponsorBlock.repositories.UserRepository;
import ru.vksponsorblock.VKSponsorBlock.repositories.RoleRepository;
import ru.vksponsorblock.VKSponsorBlock.services.UserService;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found!", id);
                    return new UserNotFoundException("User with id " + id + " not found!");
                });
    }

    @Override
    public User getByUsername(String username) {
        return userRepository
                .findUserByUsername(username)
                .orElseThrow(() ->
                {
                    log.error("User with username {} not found!", username);
                    return new UserNotFoundException("User with username " + username + " not found!");
                });
    }

    @Override
    public void deleteById(UUID id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                {
                    log.error("User with id {} not found!", id);
                    return new UserNotFoundException("User with id " + id + " not found!");
                });
        log.info("User with id {} has been deleted!", id);
    }
}
