package stenka.marcin.heroes.fraction.dto.function;

import stenka.marcin.heroes.fraction.dto.PutFractionRequest;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToFractionFunction implements BiFunction<UUID, PutFractionRequest, Fraction> {
    @Override
    public Fraction apply(UUID uuid, PutFractionRequest request) {
        return Fraction.builder()
                .id(uuid)
                .name(request.getName())
                .fractionType(request.getFractionType())
                .build();
    }
}
