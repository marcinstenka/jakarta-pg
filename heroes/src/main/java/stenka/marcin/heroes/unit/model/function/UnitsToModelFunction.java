package stenka.marcin.heroes.unit.model.function;

import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitsModel;

import java.util.List;
import java.util.function.Function;

public class UnitsToModelFunction implements Function<List<Unit>, UnitsModel> {

    @Override
    public UnitsModel apply(List<Unit> units) {
        return UnitsModel.builder()
                .units(units.stream()
                        .map(unit->UnitsModel.Unit.builder()
                                .id(unit.getId())
                                .name(unit.getName())
                                .quantity(unit.getQuantity())
                                .version(unit.getVersion())
                                .creationDateTime(unit.getCreationDateTime())
                                .modifiedDateTime(unit.getEditDateTime())
                                .build())
                        .toList()
                )
                .build();
    }
}