package stenka.marcin.heroes.fraction.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.fraction.controller.api.FractionController;
import stenka.marcin.heroes.fraction.dto.GetFractionResponse;
import stenka.marcin.heroes.fraction.dto.GetFractionsResponse;
import stenka.marcin.heroes.fraction.dto.PatchFractionRequest;
import stenka.marcin.heroes.fraction.dto.PutFractionRequest;
import stenka.marcin.heroes.fraction.service.FractionService;

import java.util.UUID;

@Path("")
public class FractionRestController implements FractionController {
    private final FractionService fractionService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public FractionRestController(final FractionService fractionService, final DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.fractionService = fractionService;
        this.uriInfo = uriInfo;
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

    @Override
    public void putFraction(UUID id, PutFractionRequest request) {
        try {
            fractionService.create(factory.requestToFraction().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(FractionController.class, "getFraction")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new NotAllowedException("Fraction already exists, to update fraction use PATCH method");
        }
    }

    @Override
    public void patchFraction(UUID id, PatchFractionRequest request) {
        fractionService.find(id)
                .ifPresentOrElse(entity -> fractionService.update(factory.updateFraction().apply(entity, request)), () -> {
                    throw new NotFoundException("Fraction not found");
                });
    }

    @Override
    public void deleteFraction(UUID id) {
        fractionService.find(id).ifPresentOrElse(entity -> fractionService.delete(id), () -> {
            throw new NotFoundException("Fraction not found");
        });
    }
}
