package stenka.marcin.heroes.fraction.controller.api;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import stenka.marcin.heroes.fraction.dto.GetFractionResponse;
import stenka.marcin.heroes.fraction.dto.GetFractionsResponse;
import stenka.marcin.heroes.fraction.dto.PatchFractionRequest;
import stenka.marcin.heroes.fraction.dto.PutFractionRequest;

import java.util.UUID;

@Path("")
public interface FractionController {
    @GET
    @Path("/fractions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetFractionResponse getFraction(@PathParam("id") UUID id);

    @GET
    @Path("/fractions")
    @Produces(MediaType.APPLICATION_JSON)
    GetFractionsResponse getFractions();

    @PUT
    @Path("/fractions/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putFraction(@PathParam("id") UUID id, PutFractionRequest request);

    @PATCH
    @Path("/fractions/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchFraction(@PathParam("id") UUID id, PatchFractionRequest request);

    @DELETE
    @Path("/fractions/{id}")
    void deleteFraction(@PathParam("id") UUID id);
}
