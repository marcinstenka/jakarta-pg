package stenka.marcin.heroes.fraction.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
public class FractionService {
    private final FractionRepository fractionRepository;

    private final UnitService unitService;

    @Inject
    public FractionService(FractionRepository fractionRepository, UnitService unitService) {
        this.fractionRepository = fractionRepository;
        this.unitService = unitService;
    }

    public FractionService() {
        this.fractionRepository = null;
        this.unitService = null;
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
        Fraction fraction = fractionRepository.find(id).orElseThrow(NotFoundException::new);
        Optional<List<Unit>> unitsToDelete = unitService.findAllByFraction(id);
        unitsToDelete.ifPresent(units -> units.forEach(unit -> {
            unitService.delete(unit.getId());
        }));
        fractionRepository.delete(fraction);
    }
}
