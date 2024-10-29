package stenka.marcin.heroes.unit.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import stenka.marcin.heroes.unit.dto.GetUnitResponse;
import stenka.marcin.heroes.unit.dto.GetUnitsResponse;
import stenka.marcin.heroes.unit.dto.PatchUnitRequest;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;

import java.util.UUID;

public interface UnitController {
    @GET
    @Path("/users/{id}/units")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitsResponse getUserUnits(@PathParam("id") UUID id);

    @GET
    @Path("/fractions/{id}/units")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitsResponse getFractionUnits(@PathParam("id") UUID id);

    @GET
    @Path("/units")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitsResponse getUnits();

    @GET
    @Path("/units/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitResponse getUnit(@PathParam("id") UUID id);

    @PUT
    @Path("/units/{id}")
    void putUnit(@PathParam("id") UUID id, PutUnitRequest request);

    @PATCH
    @Path("/units/{id}")
    void patchUnit(@PathParam("id") UUID id, PatchUnitRequest request);

    @DELETE
    @Path("/units/{id}")
    void deleteUnit(@PathParam("id") UUID id);

}
