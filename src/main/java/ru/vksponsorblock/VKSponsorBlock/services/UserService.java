package ru.vksponsorblock.VKSponsorBlock.services;

import ru.vksponsorblock.VKSponsorBlock.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User register(User user);
    List<User> getAll();
    User getById(UUID id);
    User getByUsername(String username);
    void deleteById(UUID id);
}
