package project.my.sbc2024.service;

import project.my.sbc2024.domain.model.User;

public interface UserService {

  User findById(Long id);

  User create(User userToCreate);
}

