package stenka.marcin.heroes.fraction.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import stenka.marcin.heroes.dataStore.DataStore;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class FractionInMemoryRepository implements FractionRepository {

    private final DataStore store;

    @Inject
    public FractionInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Fraction> find(UUID id) {
        return store.findAllFractions().stream().filter(fraction -> fraction.getId().equals(id)).findFirst();
    }

    @Override
    public List<Fraction> findAll() {
        return store.findAllFractions();
    }

    @Override
    public void create(Fraction entity) {
        store.createFraction(entity);
    }

    @Override
    public void delete(Fraction entity) {
        store.deleteFraction(entity.getId());
    }

    @Override
    public void update(Fraction entity) {
        store.updateFraction(entity);
    }
}
