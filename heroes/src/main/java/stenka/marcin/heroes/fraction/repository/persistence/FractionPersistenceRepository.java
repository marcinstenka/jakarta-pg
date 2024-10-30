package stenka.marcin.heroes.fraction.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class FractionPersistenceRepository implements FractionRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Fraction> find(UUID id) {
        return Optional.ofNullable(em.find(Fraction.class, id));
    }

    @Override
    public List<Fraction> findAll() {
        return em.createQuery("select f from Fraction f", Fraction.class).getResultList();
    }

    @Override
    public void create(Fraction entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Fraction entity) {
        em.remove(em.find(Fraction.class, entity.getId()));
    }

    @Override
    public void update(Fraction entity) {
        em.merge(entity);
    }
}
