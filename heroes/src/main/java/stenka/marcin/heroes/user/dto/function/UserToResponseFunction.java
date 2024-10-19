package stenka.marcin.heroes.user.dto.function;

import stenka.marcin.heroes.fraction.dto.GetFractionResponse;
import stenka.marcin.heroes.user.dto.GetUserResponse;
import stenka.marcin.heroes.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .accountCreation(user.getAccountCreation())
                .build();
    }
}

