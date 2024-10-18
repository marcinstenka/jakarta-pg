package stenka.marcin.heroes.component;

import stenka.marcin.heroes.fraction.dto.function.FractionToResponseFunction;
import stenka.marcin.heroes.fraction.dto.function.FractionsToResponseFunction;
import stenka.marcin.heroes.fraction.dto.function.RequestToFractionFunction;
import stenka.marcin.heroes.fraction.dto.function.UpdateFractionWithRequestFunction;
import stenka.marcin.heroes.unit.dto.function.RequestToUnitFunction;
import stenka.marcin.heroes.unit.dto.function.UnitToResponseFunction;
import stenka.marcin.heroes.unit.dto.function.UnitsToResponseFunction;
import stenka.marcin.heroes.unit.dto.function.UpdateUnitWithRequestFunction;
import stenka.marcin.heroes.user.dto.function.RequestToUserFunction;
import stenka.marcin.heroes.user.dto.function.UpdateUserWithRequestFunction;
import stenka.marcin.heroes.user.dto.function.UserToResponseFunction;
import stenka.marcin.heroes.user.dto.function.UsersToResponseFunction;

public class DtoFunctionFactory {

    // User entity
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

    // Unit entity
    public UnitsToResponseFunction unitsToResponse() {
        return new UnitsToResponseFunction();
    }

    public UnitToResponseFunction unitToResponse() {
        return new UnitToResponseFunction();
    }

    public RequestToUnitFunction requestToUnit() {
        return new RequestToUnitFunction();
    }

    public UpdateUnitWithRequestFunction updateUnit() {
        return new UpdateUnitWithRequestFunction();
    }

    // Fraction entity
    public FractionsToResponseFunction fractionsToResponse() {
        return new FractionsToResponseFunction();
    }

    public FractionToResponseFunction fractionToResponse() {
        return new FractionToResponseFunction();
    }

    public RequestToFractionFunction requestToFraction() {
        return new RequestToFractionFunction();
    }

    public UpdateFractionWithRequestFunction updateFraction() {
        return new UpdateFractionWithRequestFunction();
    }
}
