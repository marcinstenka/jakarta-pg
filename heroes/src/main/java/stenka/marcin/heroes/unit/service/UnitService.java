package stenka.marcin.heroes.unit.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.repository.api.UnitRepository;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.repository.api.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UnitService {
    private final UnitRepository unitRepository;

    private final UserRepository userRepository;

    private final FractionRepository fractionRepository;

    @Inject
    public UnitService(UnitRepository unitRepository, UserRepository userRepository, FractionRepository fractionRepository) {
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.fractionRepository = fractionRepository;
    }

    public Optional<Unit> find(UUID id) {
        return unitRepository.find(id);
    }

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    public void create(Unit unit, UUID userId, UUID fractionId) {
        User user = userRepository.find(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));

        Fraction fraction = fractionRepository.find(fractionId)
                .orElseThrow(() -> new NotFoundException("Fraction not found: " + fractionId));

        unitRepository.create(unit);

        List<Unit> userUnits = new ArrayList<>(user.getUnits());
        userUnits.add(unit);
        user.setUnits(userUnits);
        userRepository.update(user);

        List<Unit> fractionUnits = new ArrayList<>(fraction.getUnits());
        fractionUnits.add(unit);
        fraction.setUnits(fractionUnits);
        fractionRepository.update(fraction);
    }

    public void update(Unit unit) {
        unitRepository.update(unit);
    }

    public void delete(UUID id) {
        unitRepository.delete(unitRepository.find(id).orElseThrow(NotFoundException::new));
    }

    public Optional<List<Unit>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(unitRepository::findAllByUser);
    }

    public Optional<List<Unit>> findAllByFraction(UUID id) {
        return fractionRepository.find(id)
                .map(unitRepository::findAllByFraction);
    }
}
