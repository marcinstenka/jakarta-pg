package stenka.marcin.heroes.user.service;

import stenka.marcin.heroes.controller.servlet.exception.AlreadyExistsException;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void create(User user) {
        userRepository.create(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(UUID id) {
        userRepository.delete(userRepository.find(id).orElseThrow());
    }

    public void createAvatar(UUID id, InputStream avatar, String pathToAvatars) throws AlreadyExistsException {
        userRepository.find(id).ifPresent(user -> {
            try {
                Path destinationPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(destinationPath)) {
                    throw new AlreadyExistsException("Avatar already exists, to update avatar use PATCH method");
                }
                Files.copy(avatar, destinationPath);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

    public void updateAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        userRepository.find(id).ifPresent(user -> {
            try {
                Path existingPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(existingPath)) {
                    Files.copy(avatar, existingPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new NotFoundException();
                }
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

}
