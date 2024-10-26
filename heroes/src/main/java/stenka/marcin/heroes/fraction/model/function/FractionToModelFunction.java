package stenka.marcin.heroes.fraction.model.function;

import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.model.FractionModel;

import java.io.Serializable;
import java.util.function.Function;

public class FractionToModelFunction implements Function<Fraction, FractionModel>, Serializable {

    @Override
    public FractionModel apply(Fraction fraction) {
        return FractionModel.builder()
                .id(fraction.getId())
                .name(fraction.getName())
                .fractionType(fraction.getFractionType())
                .units(fraction.getUnits())
                .build();
    }
}
