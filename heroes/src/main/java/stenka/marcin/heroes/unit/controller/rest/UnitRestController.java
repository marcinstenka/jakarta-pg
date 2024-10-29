package stenka.marcin.heroes.unit.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.unit.controller.api.UnitController;
import stenka.marcin.heroes.unit.dto.GetUnitResponse;
import stenka.marcin.heroes.unit.dto.GetUnitsResponse;
import stenka.marcin.heroes.unit.dto.PatchUnitRequest;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;

import java.util.UUID;

public class UnitRestController implements UnitController {
    private final UnitService unitService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public UnitRestController(UnitService unitService, DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.unitService = unitService;
        this.factory = factory;
        this.uriInfo = uriInfo;

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
            unitService.create(unit, request.getUser(), request.getFraction());

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UnitController.class, "getUnit")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new NotAllowedException("Unit already exists, to update unit use PATCH method");
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Override
    public void patchUnit(UUID id, PatchUnitRequest request) {
        unitService.find(id).ifPresentOrElse(entity -> unitService.update(factory.updateUnit().apply(entity, request), entity.getFraction().getId()), () -> {
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
