package stenka.marcin.heroes.unit.controller.api;

import stenka.marcin.heroes.unit.dto.GetUnitsResponse;
import stenka.marcin.heroes.unit.dto.PatchUnitRequest;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;

import java.util.UUID;

public interface UnitController {
    GetUnitsResponse getUserUnits(UUID id);

    GetUnitsResponse getUnits();

    void putUnit(UUID id, PutUnitRequest request);

    void patchUnit(UUID id, PatchUnitRequest request);

    void deleteUnit(UUID id);

}
