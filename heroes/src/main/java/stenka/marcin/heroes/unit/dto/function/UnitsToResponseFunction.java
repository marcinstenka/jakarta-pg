package stenka.marcin.heroes.unit.dto.function;

import stenka.marcin.heroes.unit.dto.GetUnitsResponse;
import stenka.marcin.heroes.unit.entity.Unit;

import java.util.List;
import java.util.function.Function;

public class UnitsToResponseFunction implements Function<List<Unit>, GetUnitsResponse> {
    @Override
    public GetUnitsResponse apply(List<Unit> units) {
        return GetUnitsResponse.builder()
                .units(units.stream()
                        .map(unit -> GetUnitsResponse.Unit.builder()
                                .id(unit.getId())
                                .name(unit.getName())
                                .quantity(unit.getQuantity())
                                .build())
                        .toList())
                .build();
    }
}
