package stenka.marcin.heroes.fraction.model.function;

import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.model.FractionEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class FractionToEditModelFunction implements Function<Fraction, FractionEditModel>, Serializable {

    @Override
    public FractionEditModel apply(Fraction fraction) {
        return FractionEditModel.builder()
                .name(fraction.getName())
                .fractionType(fraction.getFractionType())
                .build();
    }
}
