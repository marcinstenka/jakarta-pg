package stenka.marcin.heroes.fraction.model.function;

import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.model.FractionCreateModel;

import java.io.Serializable;
import java.util.Collections;
import java.util.function.Function;

public class ModelToFractionFunction implements Function<FractionCreateModel, Fraction>, Serializable {
    @Override
    public Fraction apply(FractionCreateModel model) {
        return Fraction.builder()
                .id(model.getId())
                .name(model.getName())
                .fractionType(model.getFractionType())
                .units(Collections.emptyList())
                .build();
    }
}
