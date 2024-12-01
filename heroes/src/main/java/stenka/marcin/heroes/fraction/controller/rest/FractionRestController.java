package stenka.marcin.heroes.fraction.controller.rest;

import jakarta.annotation.security.RolesAllowed;
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
import stenka.marcin.heroes.fraction.controller.api.FractionController;
import stenka.marcin.heroes.fraction.dto.GetFractionResponse;
import stenka.marcin.heroes.fraction.dto.GetFractionsResponse;
import stenka.marcin.heroes.fraction.dto.PatchFractionRequest;
import stenka.marcin.heroes.fraction.dto.PutFractionRequest;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.user.entity.UserRoles;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed({UserRoles.ADMIN,UserRoles.USER})
public class FractionRestController implements FractionController {
    private FractionService fractionService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public FractionRestController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setFractionService(FractionService fractionService) {
        this.fractionService = fractionService;
    }

    @Override
    public GetFractionResponse getFraction(UUID id) {
        return fractionService.find(id)
                .map(factory.fractionToResponse())
                .orElseThrow(() -> new NotFoundException("Fraction not found"));
    }

    @Override
    public GetFractionsResponse getFractions() {
        return factory.fractionsToResponse().apply(fractionService.findAll());
    }
    @RolesAllowed("admin")
    @Override
    public void putFraction(UUID id, PutFractionRequest request) {
        try {
            fractionService.create(factory.requestToFraction().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(FractionController.class, "getFraction")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException("Fraction already exists, to update fraction use PATCH method");
            }
            throw ex;
        }
    }

    @Override
    public void patchFraction(UUID id, PatchFractionRequest request) {
        fractionService.find(id)
                .ifPresentOrElse(entity -> fractionService.update(factory.updateFraction().apply(entity, request)), () -> {
                    throw new NotFoundException("Fraction not found");
                });
    }
    @RolesAllowed(UserRoles.ADMIN)
    @Override
    public void deleteFraction(UUID id) {
        fractionService.find(id).ifPresentOrElse(entity -> fractionService.delete(id), () -> {
            throw new NotFoundException("Fraction not found");
        });
    }
}
