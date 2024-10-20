package stenka.marcin.heroes.fraction.dto.function;

import stenka.marcin.heroes.fraction.dto.GetFractionsResponse;
import stenka.marcin.heroes.fraction.entity.Fraction;

import java.util.List;
import java.util.function.Function;

public class FractionsToResponseFunction implements Function<List<Fraction>, GetFractionsResponse> {
    @Override
    public GetFractionsResponse apply(List<Fraction> fractions) {
        return GetFractionsResponse.builder()
                .fractions(fractions.stream()
                        .map(fraction -> GetFractionsResponse.Fraction.builder()
                                .id(fraction.getId())
                                .name(fraction.getName())
                                .build())
                        .toList())
                .build();
    }
}
