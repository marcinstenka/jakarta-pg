package stenka.marcin.heroes.fraction.dto.function;

import stenka.marcin.heroes.unit.dto.PatchUnitRequest;
import stenka.marcin.heroes.unit.entity.Unit;

import java.util.function.BiFunction;

public class UpdateFractionWithRequestFunction implements BiFunction<Unit, PatchUnitRequest, Unit> {
    @Override
    public Unit apply(Unit unit, PatchUnitRequest request) {
        return Unit.builder()
                .id(unit.getId())
                .name(request.getName())
                .quantity(request.getQuantity())
                .user(unit.getUser())
                .fraction(unit.getFraction())
                .build();
    }
}
