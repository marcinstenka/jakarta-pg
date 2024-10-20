package stenka.marcin.heroes.fraction.dto.function;

import stenka.marcin.heroes.fraction.dto.PatchFractionRequest;
import stenka.marcin.heroes.fraction.entity.Fraction;

import java.util.function.BiFunction;

public class UpdateFractionWithRequestFunction implements BiFunction<Fraction, PatchFractionRequest, Fraction> {
    @Override
    public Fraction apply(Fraction fraction, PatchFractionRequest request) {
        return Fraction.builder()
                .id(fraction.getId())
                .name(request.getName())
                .fractionType(request.getFractionType())
                .units(fraction.getUnits())
                .build();
    }
}
