package com.nexusmarket.adapters.inmemory;

import com.nexusmarket.model.enums.UserRole;
import com.nexusmarket.model.users.User;
import com.nexusmarket.ports.outbound.UserRepositoryPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepositoryPort {
    private final Map<String, User> usersById = new HashMap<>();

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        usersById.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(usersById.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return usersById.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return usersById.values().stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }
}
