package stenka.marcin.heroes.unit.model.function;

import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.model.FractionModel;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class UnitToEditModelFunction implements Function<Unit, UnitEditModel>, Serializable {

    @Override
    public UnitEditModel apply(Unit unit) {
        return UnitEditModel.builder()
                .name(unit.getName())
                .quantity(unit.getQuantity())
                .user(unit.getUser())
                .fraction(FractionModel.builder()
                        .id(unit.getFraction().getId())
                        .name(unit.getFraction().getName())
                        .fractionType(unit.getFraction().getFractionType())
                        .units(unit.getFraction().getUnits())
                        .build())
                .build();
    }
}
