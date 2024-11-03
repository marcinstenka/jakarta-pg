package stenka.marcin.heroes.unit.controller.rest;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.unit.controller.api.UnitController;
import stenka.marcin.heroes.unit.dto.GetUnitResponse;
import stenka.marcin.heroes.unit.dto.GetUnitsResponse;
import stenka.marcin.heroes.unit.dto.PatchUnitRequest;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class UnitRestController implements UnitController {
    private UnitService unitService;

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

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
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
    public GetUnitResponse getFractionUnit(UUID fractionId, UUID unitId) {
        return unitService.findByFractionAndUnit(fractionId, unitId)
                .map(factory.unitToResponse())
                .orElseThrow(() -> new NotFoundException("Unit not found in the specified fraction"));
    }

    @Override
    public void putFractionUnit(UUID fractionId, UUID unitId, PutUnitRequest request) {
        try {
            request.setFraction(fractionId);
            Unit unit = factory.requestToUnit().apply(unitId, request);
            unitService.create(unit, request.getUser(), fractionId);

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UnitController.class, "getUnit")
                    .build(unitId)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException("Unit already exists, to update unit use PATCH method");
            }
            throw ex;
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Override
    public void patchFractionUnit(UUID fractionId, UUID unitId, PatchUnitRequest request) {
        unitService.findByFractionAndUnit(fractionId, unitId).ifPresentOrElse(
                entity -> unitService.update(factory.updateUnit().apply(entity, request), fractionId),
                () -> {
                    throw new NotFoundException("Unit not found in the specified fraction");
                });
    }

    @Override
    public void deleteFractionUnit(UUID fractionId, UUID unitId) {
        unitService.findByFractionAndUnit(fractionId, unitId).ifPresentOrElse(
                entity -> unitService.delete(unitId),
                () -> {
                    throw new NotFoundException("Unit not found in the specified fraction");
                });
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

}
