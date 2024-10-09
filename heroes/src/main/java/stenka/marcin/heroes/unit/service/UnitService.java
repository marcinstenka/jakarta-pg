package stenka.marcin.heroes.unit.service;

import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.repository.api.UnitRepository;
import stenka.marcin.heroes.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UnitService {
    private final UnitRepository unitRepository;

    private final UserRepository userRepository;

    public UnitService(UnitRepository unitRepository, UserRepository userRepository) {
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
    }

    public Optional<List<Unit>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(unitRepository::findAllByUser);
    }
}
