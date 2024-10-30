package stenka.marcin.heroes.unit.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.repository.api.UnitRepository;
import stenka.marcin.heroes.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UnitPersistenceRepository implements UnitRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Unit> findAllByUser(User user) {
        return em.createQuery("select u from Unit u where u.user = :user", Unit.class)
                .setParameter("user", user)
                .getResultList();

    }

    @Override
    public List<Unit> findAllByFraction(Fraction fraction) {
        return em.createQuery("select u from Unit u where u.fraction = :fraction", Unit.class)
                .setParameter("fraction", fraction)
                .getResultList();
    }

    @Override
    public Optional<Unit> find(UUID id) {
        return Optional.ofNullable(em.find(Unit.class, id));
    }

    @Override
    public List<Unit> findAll() {
        return em.createQuery("select u from Unit u", Unit.class).getResultList();
    }

    @Override
    public void create(Unit entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Unit entity) {
        em.remove(em.find(Unit.class, entity.getId()));
    }

    @Override
    public void update(Unit entity) {
        em.merge(entity);
    }
}
