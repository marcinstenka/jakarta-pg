package stenka.marcin.heroes.unit.dto.function;

import lombok.SneakyThrows;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitEditModel;

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
                .user(unit.getUser())
                .fraction(Fraction.builder()
                        .id(request.getFraction().getId())
                        .name(request.getFraction().getName())
                        .fractionType(request.getFraction().getFractionType())
                        .units(request.getFraction().getUnits())
                        .build())
                .build();
    }
}
