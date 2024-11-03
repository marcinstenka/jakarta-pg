package stenka.marcin.heroes.user.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import stenka.marcin.heroes.user.dto.GetUserResponse;
import stenka.marcin.heroes.user.dto.GetUsersResponse;
import stenka.marcin.heroes.user.dto.PatchUserRequest;
import stenka.marcin.heroes.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;
@Path("")
public interface UserController {
    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @PUT
    @Path("/users/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putUser(@PathParam("id") UUID id, PutUserRequest request);

    @PATCH
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchUser(@PathParam("id") UUID id, PatchUserRequest request);

    @DELETE
    @Path("/users/{id}")
    void deleteUser(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/avatar")
    @Produces("image/png")
    byte[] getUserAvatar(UUID id, String pathToAvatars);

    @PUT
    @Path("/users/{id}/avatar")
    @Consumes({MediaType.APPLICATION_JSON})
    void putUserAvatar(UUID id, InputStream avatar, String pathToAvatars);

    @DELETE
    @Path("/users/{id}/avatar")
    void deleteUserAvatar(UUID id, String pathToAvatars);

    @PATCH
    @Path("/users/{id}/avatar")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchUserAvatar(UUID id, InputStream avatar, String pathToAvatars);
}
