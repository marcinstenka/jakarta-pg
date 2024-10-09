package stenka.marcin.heroes.user.dto.function;

import stenka.marcin.heroes.user.dto.GetUsersResponse;
import stenka.marcin.heroes.user.entity.User;

import java.util.List;
import java.util.function.Function;

public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(List<User> users) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .build())
                        .toList())
                .build();
    }
}

