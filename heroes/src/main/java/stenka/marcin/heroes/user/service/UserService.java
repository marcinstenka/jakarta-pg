package stenka.marcin.heroes.user.service;

import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.repository.api.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    public Optional<User> find(String name) {
        return userRepository.findByName(name);
    }

    public void create(User user) {
        userRepository.create(user);
    }
}
