package stenka.marcin.heroes.user.controller.api;

import stenka.marcin.heroes.user.dto.GetUserResponse;
import stenka.marcin.heroes.user.dto.GetUsersResponse;
import stenka.marcin.heroes.user.dto.PatchUserRequest;
import stenka.marcin.heroes.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {
    GetUserResponse getUser(UUID id);

    GetUsersResponse getUsers();

    void putUser(UUID id, PutUserRequest request);

    void patchUser(UUID id, PatchUserRequest request);

    void deleteUser(UUID id);

    byte[] getUserAvatar(UUID id, String pathToAvatars);

    void putUserAvatar(UUID id, InputStream avatar, String pathToAvatars);

    void deleteUserAvatar(UUID id, String pathToAvatars);

    void patchUserAvatar(UUID id, InputStream avatar, String pathToAvatars);
}
