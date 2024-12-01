package stenka.marcin.heroes.user.model.function;

import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.model.UserModel;

import java.io.Serializable;
import java.util.function.Function;

public class UserToModelFunction implements Function<User, UserModel>, Serializable {
    @Override
    public UserModel apply(User user) {
        return UserModel.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}