package stenka.marcin.heroes.unit.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.repository.api.UnitRepository;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UnitService {
    private final UnitRepository unitRepository;

    private final UserService userService;

    private final FractionService fractionService;

    @Inject
    public UnitService(UnitRepository unitRepository, UserService userService, FractionService fractionService) {
        this.unitRepository = unitRepository;
        this.userService = userService;
        this.fractionService = fractionService;
    }

    public UnitService() {
        this.unitRepository = null;
        this.userService = null;
        this.fractionService = null;
    }

    public Optional<Unit> find(UUID id) {
        return unitRepository.find(id);
    }

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Transactional
    public void create(Unit unit, UUID userId, UUID fractionId) {

        User user = userService.find(userId).orElseThrow(() -> new NotFoundException("User not found: " + userId));

        Fraction fraction = fractionService.find(fractionId).orElseThrow(() -> new NotFoundException("Fraction not found: " + fractionId));

        unitRepository.create(unit);

        List<Unit> userUnits = new ArrayList<>(user.getUnits());
        userUnits.add(unit);
        user.setUnits(userUnits);
        userService.update(user);

        List<Unit> fractionUnits = new ArrayList<>(fraction.getUnits());
        fractionUnits.add(unit);
        fraction.setUnits(fractionUnits);
        fractionService.update(fraction);
    }

    @Transactional
    public void update(Unit unit, UUID initialFraction) {
        User user = userService.find(unit.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found: " + unit.getUser().getId()));

        Fraction newFraction = fractionService.find(unit.getFraction().getId())
                .orElseThrow(() -> new NotFoundException("Fraction not found: " + unit.getFraction().getId()));

        if (!initialFraction.equals(newFraction.getId())) {
            Fraction oldFraction = fractionService.find(initialFraction)
                    .orElseThrow(() -> new NotFoundException("Initial fraction not found: " + initialFraction));

            oldFraction.getUnits().removeIf(oldFractionUnit -> oldFractionUnit.getId().equals(unit.getId()));
            fractionService.update(oldFraction);
        }

        boolean userUnitUpdated = user.getUnits().removeIf(userUnit -> userUnit.getId().equals(unit.getId()));
        if (userUnitUpdated) {
            user.getUnits().add(unit);
        } else {
            throw new NotFoundException("Unit not found in user's units: " + unit.getId());
        }

        newFraction.getUnits().removeIf(fractionUnit -> fractionUnit.getId().equals(unit.getId()));
        newFraction.getUnits().add(unit);

        userService.update(user);
        fractionService.update(newFraction);
        unitRepository.update(unit);
    }

    @Transactional
    public void delete(UUID id) {
        Unit unit = unitRepository.find(id)
                .orElseThrow(NotFoundException::new);

        User user = userService.find(unit.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found: " + unit.getUser().getId()));

        Fraction fraction = fractionService.find(unit.getFraction().getId())
                .orElseThrow(() -> new NotFoundException("Fraction not found: " + unit.getFraction().getId()));

        user.getUnits().removeIf(u -> u.getId().equals(unit.getId()));
        fraction.getUnits().removeIf(f -> f.getId().equals(unit.getId()));

        userService.update(user);
        fractionService.update(fraction);
        unitRepository.delete(unit);
    }

    public Optional<List<Unit>> findAllByUser(UUID id) {
        return userService.find(id)
                .map(unitRepository::findAllByUser);
    }

    public Optional<List<Unit>> findAllByFraction(UUID id) {
        return fractionService.find(id)
                .map(unitRepository::findAllByFraction);
    }
}
