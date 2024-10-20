package stenka.marcin.heroes.fraction.service;

import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FractionService {
    private final FractionRepository fractionRepository;

    private final UnitService unitService;

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

    public void addUnitToFraction(UUID fractionId, Unit unit) {
        fractionRepository.find(fractionId).ifPresentOrElse(
                fraction -> {
                    List<Unit> units = new ArrayList<>(fraction.getUnits());
                    units.add(unit);
                    fraction.setUnits(units);
                    fractionRepository.update(fraction);
                },
                NotFoundException::new);
    }

}
