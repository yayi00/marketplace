package com.nexusmarket.ports.outbound;

import com.nexusmarket.model.users.User;
import com.nexusmarket.model.enums.UserRole;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
}
