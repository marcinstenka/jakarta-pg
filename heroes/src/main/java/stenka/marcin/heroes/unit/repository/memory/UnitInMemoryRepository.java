package stenka.marcin.heroes.unit.repository.memory;

import stenka.marcin.heroes.dataStore.DataStore;
import stenka.marcin.heroes.fraction.entity.Fraction;
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
    public Optional<Unit> find(UUID id) {
        return store.findAllUnits().stream().filter(unit -> id.equals(unit.getId())).findFirst();
    }

    @Override
    public List<Unit> findAll() {
        return store.findAllUnits();
    }

    @Override
    public void create(Unit entity) {
        store.createUnit(entity);
    }

    @Override
    public void delete(Unit entity) {
        store.deleteUnit(entity.getId());
    }

    @Override
    public void update(Unit entity) {
        store.updateUnit(entity);
    }

    @Override
    public List<Unit> findAllByUser(User user) {
        return store.findAllUnits().stream()
                .filter(unit ->
                        user.getId().equals(unit.getUser().getId())
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Unit> findAllByFraction(Fraction fraction) {
        return store.findAllUnits().stream()
                .filter(unit ->
                        fraction.getId().equals(unit.getFraction().getId())
                )
                .collect(Collectors.toList());
    }
}
