package stenka.marcin.heroes.component;

import stenka.marcin.heroes.unit.dto.function.UnitsToResponseFunction;
import stenka.marcin.heroes.user.dto.function.RequestToUserFunction;
import stenka.marcin.heroes.user.dto.function.UpdateUserWithRequestFunction;
import stenka.marcin.heroes.user.dto.function.UserToResponseFunction;
import stenka.marcin.heroes.user.dto.function.UsersToResponseFunction;

public class DtoFunctionFactory {

    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }

    public UnitsToResponseFunction unitsToResponse() {
        return new UnitsToResponseFunction();
    }
}
