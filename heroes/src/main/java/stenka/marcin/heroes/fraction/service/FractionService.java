package stenka.marcin.heroes.fraction.service;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
@NoArgsConstructor(force = true)
public class FractionService {
    private final FractionRepository fractionRepository;

    private final UnitService unitService;

    @Inject
    public FractionService(FractionRepository fractionRepository, UnitService unitService) {
        this.fractionRepository = fractionRepository;
        this.unitService = unitService;
    }

    public Optional<Fraction> find(UUID id) {
        return fractionRepository.find(id);
    }

    public List<Fraction> findAll() {
        return fractionRepository.findAll();
    }

    public void create(Fraction fraction) {
        fractionRepository.create(fraction);
    }

    public void update(Fraction fraction) {
        fractionRepository.update(fraction);
    }

    public void delete(UUID id) {
        fractionRepository.delete(fractionRepository.find(id).orElseThrow(NotFoundException::new));
        Optional<List<Unit>> unitsToDelete = unitService.findAllByUser(id);
        unitsToDelete.ifPresent(units -> units.forEach(unit -> {
            unitService.delete(unit.getId());
        }));
    }
}
