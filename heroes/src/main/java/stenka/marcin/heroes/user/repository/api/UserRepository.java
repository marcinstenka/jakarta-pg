package stenka.marcin.heroes.user.repository.api;

import stenka.marcin.heroes.repository.api.Repository;
import stenka.marcin.heroes.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {

    Optional<User> findByName(String name);
}
