package stenka.marcin.heroes.unit.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import stenka.marcin.heroes.unit.dto.GetUnitResponse;
import stenka.marcin.heroes.unit.dto.GetUnitsResponse;
import stenka.marcin.heroes.unit.dto.PatchUnitRequest;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;

import java.util.UUID;
@Path("")
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
    @Path("/fractions/{fractionId}/units/{unitId}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitResponse getFractionUnit(@PathParam("fractionId") UUID fractionId, @PathParam("unitId") UUID unitId);

    @PUT
    @Path("/fractions/{fractionId}/units/{unitId}")
    void putFractionUnit(@PathParam("fractionId") UUID fractionId, @PathParam("unitId") UUID unitId, PutUnitRequest request);

    @PATCH
    @Path("/fractions/{fractionId}/units/{unitId}")
    void patchFractionUnit(@PathParam("fractionId") UUID fractionId, @PathParam("unitId") UUID unitId, PatchUnitRequest request);

    @DELETE
    @Path("/fractions/{fractionId}/units/{unitId}")
    void deleteFractionUnit(@PathParam("fractionId") UUID fractionId, @PathParam("unitId") UUID unitId);

    // Test only
    @GET
    @Path("/units")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitsResponse getUnits();

    @GET
    @Path("/units/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitResponse getUnit(@PathParam("id") UUID id);


}
