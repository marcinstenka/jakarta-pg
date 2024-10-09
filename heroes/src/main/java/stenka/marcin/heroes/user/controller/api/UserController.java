package stenka.marcin.heroes.user.controller.api;

import stenka.marcin.heroes.user.dto.GetUserResponse;
import stenka.marcin.heroes.user.dto.GetUsersResponse;
import stenka.marcin.heroes.user.dto.PatchUserRequest;
import stenka.marcin.heroes.user.dto.PutUserRequest;

import java.util.UUID;

public interface UserController {
    GetUserResponse getUser(UUID id);

    GetUsersResponse getUsers();

    void putUser(UUID id, PutUserRequest request);

    void patchUser(UUID id, PatchUserRequest request);

    void deleteUser(UUID id);
}
