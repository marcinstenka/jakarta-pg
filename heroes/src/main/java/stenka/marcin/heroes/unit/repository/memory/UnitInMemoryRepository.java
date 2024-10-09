package stenka.marcin.heroes.unit.repository.memory;

import stenka.marcin.heroes.dataStore.DataStore;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.repository.api.UnitRepository;
import stenka.marcin.heroes.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UnitInMemoryRepository implements UnitRepository {

    private final DataStore store;

    public UnitInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public List<Unit> findAllByUser(User user) {
        return store.findAllUnits().stream()
                .filter(unit -> user.equals(unit.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Character> find(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Character> findAll() {
        return List.of();
    }

    @Override
    public void create(Character entity) {

    }

    @Override
    public void delete(Character entity) {

    }

    @Override
    public void update(Character entity) {

    }
}
