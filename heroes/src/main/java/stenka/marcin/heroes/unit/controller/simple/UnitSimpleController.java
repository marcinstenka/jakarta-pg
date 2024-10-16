package stenka.marcin.heroes.unit.controller.simple;

import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.unit.controller.api.UnitController;
import stenka.marcin.heroes.unit.dto.GetUnitsResponse;
import stenka.marcin.heroes.unit.service.UnitService;

import java.util.UUID;

public class UnitSimpleController implements UnitController {

    private final UnitService unitService;

    private final DtoFunctionFactory factory;

    public UnitSimpleController(UnitService unitService, DtoFunctionFactory factory) {
        this.factory = factory;
        this.unitService = unitService;
    }

    @Override
    public GetUnitsResponse getUserUnits(UUID id) {
        return unitService.findAllByUser(id)
                .map(factory.unitsToResponse())
                .orElseThrow(NotFoundException::new);
    }


}
