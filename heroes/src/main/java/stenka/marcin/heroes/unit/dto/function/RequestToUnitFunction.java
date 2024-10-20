package stenka.marcin.heroes.unit.dto.function;

import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUnitFunction implements BiFunction<UUID, PutUnitRequest, Unit> {
    @Override
    public Unit apply(UUID uuid, PutUnitRequest request) {
        return Unit.builder()
                .id(uuid)
                .name(request.getName())
                .quantity(request.getQuantity())
                .user(User.builder().id(request.getUser()).build())
                .fraction(Fraction.builder().id(request.getFraction()).build())
                .build();
    }
}
