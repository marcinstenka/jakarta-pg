package stenka.marcin.heroes.unit.repository.api;

import stenka.marcin.heroes.repository.api.Repository;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface UnitRepository extends Repository<Character, UUID> {

    List<Unit> findAllByUser(User user);
}
