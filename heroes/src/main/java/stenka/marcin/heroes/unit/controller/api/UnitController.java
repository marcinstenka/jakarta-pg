package stenka.marcin.heroes.unit.controller.api;

import stenka.marcin.heroes.unit.dto.GetUnitsResponse;

import java.util.UUID;

public interface UnitController {
    GetUnitsResponse getUserUnits(UUID id);

    GetUnitsResponse getUnits();

}
