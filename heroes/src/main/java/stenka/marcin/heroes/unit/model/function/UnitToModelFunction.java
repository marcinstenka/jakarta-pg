package stenka.marcin.heroes.unit.model.function;

import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitModel;

import java.io.Serializable;
import java.util.function.Function;

public class UnitToModelFunction implements Function<Unit, UnitModel>, Serializable {

    @Override
    public UnitModel apply(Unit unit) {
        return UnitModel.builder()
                .id(unit.getId())
                .name(unit.getName())
                .quantity(unit.getQuantity())
                .version(unit.getVersion())
                .creationDateTime(unit.getCreationDateTime())
                .build();
    }
}
