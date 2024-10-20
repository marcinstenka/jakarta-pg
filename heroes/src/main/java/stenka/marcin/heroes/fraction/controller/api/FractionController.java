package stenka.marcin.heroes.fraction.controller.api;

import stenka.marcin.heroes.fraction.dto.GetFractionResponse;
import stenka.marcin.heroes.fraction.dto.GetFractionsResponse;
import stenka.marcin.heroes.fraction.dto.PatchFractionRequest;
import stenka.marcin.heroes.fraction.dto.PutFractionRequest;

import java.util.UUID;

public interface FractionController {
    GetFractionResponse getFraction(UUID id);

    GetFractionsResponse getFractions();

    void putFraction(UUID id, PutFractionRequest request);

    void patchFraction(UUID id, PatchFractionRequest request);

    void deleteFraction(UUID id);
}
