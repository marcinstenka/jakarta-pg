package stenka.marcin.heroes.user.dto.function;

import stenka.marcin.heroes.user.dto.PutUserRequest;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.entity.UserRoles;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {

    @Override
    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .name(request.getName())
                .accountCreation(request.getAccountCreation())
                .password(request.getPassword())
                .roles(List.of(UserRoles.USER))
                .units(Collections.emptyList())
                .build();
    }
}
