package stenka.marcin.heroes.unit.model.function;

import lombok.SneakyThrows;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitEditModel;
import stenka.marcin.heroes.user.entity.User;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateUnitWithModelFunction implements BiFunction<Unit, UnitEditModel, Unit>, Serializable {
    @Override
    @SneakyThrows
    public Unit apply(Unit unit, UnitEditModel request) {
        return Unit.builder()
                .id(unit.getId())
                .name(request.getName())
                .quantity(request.getQuantity())
                .fraction(Fraction.builder()
                        .id(request.getFraction().getId())
                        .name(request.getFraction().getName())
                        .units(request.getFraction().getUnits())
                        .fractionType(request.getFraction().getFractionType())
                        .build())
                .user(User.builder()
                        .id(request.getUser().getId())
                        .build())
                .version(request.getVersion())
                .creationDateTime(unit.getCreationDateTime())
                .build();
    }
}
