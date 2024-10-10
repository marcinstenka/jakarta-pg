package stenka.marcin.heroes.user.controller.simple;

import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.controller.servlet.exception.BadRequestException;
import stenka.marcin.heroes.user.controller.api.UserController;
import stenka.marcin.heroes.user.dto.GetUserResponse;
import stenka.marcin.heroes.user.dto.GetUsersResponse;
import stenka.marcin.heroes.user.dto.PatchUserRequest;
import stenka.marcin.heroes.user.dto.PutUserRequest;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.service.UserService;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class UserSimpleController implements UserController {

    private final UserService userService;

    private final DtoFunctionFactory factory;

    public UserSimpleController(DtoFunctionFactory factory, UserService userService) {
        this.factory = factory;
        this.userService = userService;
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return userService.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
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
            throw new BadRequestException(ex);
        }

    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request) {
        userService.find(id).ifPresentOrElse(entity -> userService.update(factory.updateUser().apply(entity, request)), () -> {
            throw new NotFoundException();
        });

    }

    @Override
    public void deleteUser(UUID id) {
        userService.find(id).ifPresentOrElse(
                entity -> userService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getUserAvatar(UUID id) {
        Path pathToAvatar = Paths.get(userService.find(id).map(User::getAvatarPath).orElseThrow(NotFoundException::new));
        try {
            return Files.readAllBytes(pathToAvatar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putUserAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        userService.find(id).ifPresentOrElse(
                entity -> ,// Tutaj zrobic zabawę na plikach
                () -> {
                    throw new NotFoundException();
                }
        );

    }
}
