package stenka.marcin.heroes.fraction.model.function;

import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.model.FractionsModel;

import java.util.List;
import java.util.function.Function;

public class FractionsToModelFunction implements Function<List<Fraction>, FractionsModel> {
    @Override
    public FractionsModel apply(List<Fraction> fractions) {
        return FractionsModel.builder()
                .fractions(fractions.stream()
                        .map(fraction -> FractionsModel.Fraction.builder()
                                .id(fraction.getId())
                                .name(fraction.getName())
                                .build())
                        .toList())
                .build();
    }
}
