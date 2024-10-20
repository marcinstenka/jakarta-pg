package stenka.marcin.heroes.fraction.dto.function;

import stenka.marcin.heroes.fraction.dto.GetFractionResponse;
import stenka.marcin.heroes.fraction.entity.Fraction;

import java.util.function.Function;

public class FractionToResponseFunction implements Function<Fraction, GetFractionResponse> {
    @Override
    public GetFractionResponse apply(Fraction fraction) {
        return GetFractionResponse.builder()
                .id(fraction.getId())
                .name(fraction.getName())
                .fractionType(fraction.getFractionType())
                .units(fraction.getUnits().stream()
                        .map(unit -> GetFractionResponse.Unit.builder()
                                .id(unit.getId())
                                .name(unit.getName())
                                .build())
                        .toList())
                .build();
    }
}
