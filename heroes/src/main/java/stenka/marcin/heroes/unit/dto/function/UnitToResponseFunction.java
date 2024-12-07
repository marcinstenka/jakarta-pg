package stenka.marcin.heroes.unit.dto.function;

import stenka.marcin.heroes.unit.dto.GetUnitResponse;
import stenka.marcin.heroes.unit.entity.Unit;

import java.util.function.Function;

public class UnitToResponseFunction implements Function<Unit, GetUnitResponse> {
    @Override
    public GetUnitResponse apply(Unit unit) {
        return GetUnitResponse.builder()
                .id(unit.getId())
                .name(unit.getName())
                .quantity(unit.getQuantity())
                .version(unit.getVersion())
                .user(GetUnitResponse.User.builder()
                        .id(unit.getUser().getId())
                        .build())
                .fraction(GetUnitResponse.Fraction.builder()
                        .id(unit.getFraction().getId())
                        .build())
                .build();
    }
}
