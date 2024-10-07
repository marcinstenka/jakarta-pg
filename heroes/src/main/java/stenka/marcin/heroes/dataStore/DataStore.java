package stenka.marcin.heroes.dataStore;

import lombok.extern.java.Log;
import stenka.marcin.heroes.serialization.CloningUtility;
import stenka.marcin.heroes.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log
public class DataStore {

    private final Set<User> users = new HashSet<>();

    private final CloningUtility cloningUtility;

    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

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


}
