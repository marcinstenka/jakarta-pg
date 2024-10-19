package stenka.marcin.heroes.unit.controller.simple;

import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.controller.servlet.exception.AlreadyExistsException;
import stenka.marcin.heroes.controller.servlet.exception.BadRequestException;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.unit.controller.api.UnitController;
import stenka.marcin.heroes.unit.dto.GetUnitResponse;
import stenka.marcin.heroes.unit.dto.GetUnitsResponse;
import stenka.marcin.heroes.unit.dto.PatchUnitRequest;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;
import stenka.marcin.heroes.user.service.UserService;

import java.util.UUID;

public class UnitSimpleController implements UnitController {

    private final UnitService unitService;

    private final UserService userService;

    private final FractionService fractionService;

    private final DtoFunctionFactory factory;

    public UnitSimpleController(UnitService unitService, UserService userService, FractionService fractionService, DtoFunctionFactory factory) {
        this.factory = factory;
        this.unitService = unitService;
        this.userService = userService;
        this.fractionService = fractionService;
    }

    @Override
    public GetUnitsResponse getUserUnits(UUID id) {
        return unitService.findAllByUser(id)
                .map(factory.unitsToResponse())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public GetUnitsResponse getFractionUnits(UUID id) {
        return unitService.findAllByFraction(id)
                .map(factory.unitsToResponse())
                .orElseThrow(() -> new NotFoundException("Fraction not found"));
    }

    @Override
    public GetUnitsResponse getUnits() {
        return factory.unitsToResponse().apply(unitService.findAll());
    }

    @Override
    public GetUnitResponse getUnit(UUID id) {
        return unitService.find(id)
                .map(factory.unitToResponse())
                .orElseThrow(() -> new NotFoundException("Unit not found"));
    }

    @Override
    public void putUnit(UUID id, PutUnitRequest request) {
        try {
            Unit unit = factory.requestToUnit().apply(id, request);
            unitService.create(unit);
            userService.addUnitToList(request.getUser(), unit);
            fractionService.addUnitToFraction(request.getFraction(), unit);
        } catch (IllegalArgumentException ex) {
            throw new AlreadyExistsException("Unit already exists, to update unit use PATCH method");
        } catch (NotFoundException ex) {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public void patchUnit(UUID id, PatchUnitRequest request) {
        unitService.find(id).ifPresentOrElse(entity -> unitService.update(factory.updateUnit().apply(entity, request)), () -> {
            throw new NotFoundException("Unit not found");
        });
    }

    @Override
    public void deleteUnit(UUID id) {
        unitService.find(id).ifPresentOrElse(entity -> unitService.delete(id), () -> {
            throw new NotFoundException("Unit not found");
        });
    }


}
