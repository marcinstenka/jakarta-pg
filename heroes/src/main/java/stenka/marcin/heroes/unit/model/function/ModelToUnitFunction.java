package stenka.marcin.heroes.unit.model.function;

import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToUnitFunction implements Function<UnitCreateModel, Unit>, Serializable {
    @Override
    public Unit apply(UnitCreateModel model) {
        return Unit.builder()
                .id(model.getId())
                .name(model.getName())
                .quantity(model.getQuantity())
                .user(model.getUser())
                .fraction(Fraction.builder()
                        .id(model.getFraction().getId())
                        .name(model.getFraction().getName())
                        .fractionType(model.getFraction().getFractionType())
                        .units(model.getFraction().getUnits())
                        .build())
                .build();
    }
}
