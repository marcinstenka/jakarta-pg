package stenka.marcin.heroes.user.dto.function;

import stenka.marcin.heroes.user.dto.PatchUserRequest;
import stenka.marcin.heroes.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserWithRequestFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User entity, PatchUserRequest request) {
        return User.builder()
                .id(entity.getId())
                .name(request.getName())
                .accountCreation(request.getAccountCreation())
                .units(entity.getUnits())
                .build();
    }

}
