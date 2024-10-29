package stenka.marcin.heroes.user.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.user.controller.api.UserController;
import stenka.marcin.heroes.user.dto.GetUserResponse;
import stenka.marcin.heroes.user.dto.GetUsersResponse;
import stenka.marcin.heroes.user.dto.PatchUserRequest;
import stenka.marcin.heroes.user.dto.PutUserRequest;
import stenka.marcin.heroes.user.service.UserService;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequestScoped
public class UserSimpleController implements UserController {

    private final UserService userService;

    private final DtoFunctionFactory factory;

    @Inject
    public UserSimpleController(DtoFunctionFactory factory, UserService userService) {
        this.factory = factory;
        this.userService = userService;
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return userService.find(id)
                .map(factory.userToResponse())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(userService.findAll());
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try {
            userService.create(factory.requestToUser().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new NotAllowedException("User already exists, to update user use PATCH method");
        }
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request) {
        userService.find(id).ifPresentOrElse(entity -> userService.update(factory.updateUser().apply(entity, request)), () -> {
            throw new NotFoundException("User not found, to create user use PUT method");
        });

    }

    @Override
    public void deleteUser(UUID id) {
        userService.find(id).ifPresentOrElse(
                entity -> userService.delete(id),
                () -> {
                    throw new NotFoundException("User not found");
                }
        );
    }

    @Override
    public byte[] getUserAvatar(UUID id, String pathToAvatars) {
        Path pathToAvatar = Paths.get(
                pathToAvatars,
                userService.find(id)
                        .map(user -> user.getId().toString())
                        .orElseThrow(() -> new NotFoundException("User does not exist"))
                        + ".png"
        );
        try {
            if (!Files.exists(pathToAvatar)) {
                throw new NotFoundException("User avatar does not exist");
            }
            return Files.readAllBytes(pathToAvatar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putUserAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        userService.find(id).ifPresentOrElse(
                user -> {
                    userService.createAvatar(id, avatar, pathToAvatars);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUserAvatar(UUID id, String pathToAvatars) {
        userService.find(id).ifPresentOrElse(
                user -> {
                    try {
                        Path avatarPath = Paths.get(pathToAvatars, user.getId().toString() + ".png");
                        if (!Files.exists(avatarPath)) {
                            throw new NotFoundException("User avatar does not exist");
                        }
                        Files.delete(avatarPath);
                    } catch (IOException e) {
                        throw new NotFoundException(e);
                    }
                },
                () -> {
                    throw new NotFoundException("User does not exist");
                }
        );
    }

    @Override
    public void patchUserAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        userService.find(id).ifPresentOrElse(
                user -> userService.updateAvatar(id, avatar, pathToAvatars),
                () -> {
                    throw new NotFoundException("User does not exist");
                }
        );
    }

}
