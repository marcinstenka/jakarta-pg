package stenka.marcin.heroes.fraction.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.controller.servlet.exception.AlreadyExistsException;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.fraction.controller.api.FractionController;
import stenka.marcin.heroes.fraction.dto.GetFractionResponse;
import stenka.marcin.heroes.fraction.dto.GetFractionsResponse;
import stenka.marcin.heroes.fraction.dto.PatchFractionRequest;
import stenka.marcin.heroes.fraction.dto.PutFractionRequest;
import stenka.marcin.heroes.fraction.service.FractionService;

import java.util.UUID;

@RequestScoped
public class FractionSimpleController implements FractionController {
    private final FractionService fractionService;

    private final DtoFunctionFactory factory;

    @Inject
    public FractionSimpleController(final FractionService fractionService, final DtoFunctionFactory factory) {
        this.factory = factory;
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

    @Override
    public void putFraction(UUID id, PutFractionRequest request) {
        try {
            fractionService.create(factory.requestToFraction().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new AlreadyExistsException("Fraction already exists, to update fraction use PATCH method");
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
