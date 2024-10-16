package stenka.marcin.heroes.dataStore;

import lombok.extern.java.Log;
import stenka.marcin.heroes.serialization.CloningUtility;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.unit.entity.Unit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
public class DataStore {

    private final Set<User> users = new HashSet<>();

    private final Set<Unit> units = new HashSet<>();

    private final CloningUtility cloningUtility;

    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    // User entity
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteUser(UUID id) throws IllegalArgumentException {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(id));
        }
    }

    //Unit entity
    public synchronized List<Unit> findAllUnits() {
        return units.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUnit(Unit value) throws IllegalArgumentException {
        if (units.stream().anyMatch(unit -> unit.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The unit id \"%s\" is not unique".formatted(value.getId()));
        }
        units.add(cloningUtility.clone(value));
    }

    public synchronized void updateUnit(Unit value) throws IllegalArgumentException {
        if (units.removeIf(unit -> unit.getId().equals(value.getId()))) {
            units.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The unit with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteUnit(UUID id) throws IllegalArgumentException {
        if (!units.removeIf(unit -> unit.getId().equals(id))) {
            throw new IllegalArgumentException("The unit with id \"%s\" does not exist".formatted(id));
        }
    }
}
