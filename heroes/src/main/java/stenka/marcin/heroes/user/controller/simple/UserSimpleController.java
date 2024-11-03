package stenka.marcin.heroes.user.controller.simple;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
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
import java.nio.file.Paths;
import java.util.UUID;

@Path("")
public class UserSimpleController implements UserController {

    private final UserService userService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


    @Inject
    public UserSimpleController(DtoFunctionFactory factory, UserService userService, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.userService = userService;
        this.uriInfo = uriInfo;
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
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new NotAllowedException("User already exists, to update User use PATCH method");
        }
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request) {
        userService.find(id)
                .ifPresentOrElse(entity -> userService.update(factory.updateUser().apply(entity, request)), () -> {
                    throw new NotFoundException("User not found");
                });

    }

    @Override
    public void deleteUser(UUID id) {
        userService.find(id).ifPresentOrElse(entity -> userService.delete(id), () -> {
            throw new NotFoundException("Fraction not found");
        });
    }

    @Override
    public byte[] getUserAvatar(UUID id, String pathToAvatars) {

        java.nio.file.Path pathToAvatar = Paths.get(
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
                        java.nio.file.Path avatarPath = Paths.get(pathToAvatars, user.getId().toString() + ".png");
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
