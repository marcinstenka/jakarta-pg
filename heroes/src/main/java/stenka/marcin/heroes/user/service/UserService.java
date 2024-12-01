package stenka.marcin.heroes.user.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.NotAllowedException;
import lombok.NoArgsConstructor;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.entity.UserRoles;
import stenka.marcin.heroes.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository userRepository;

    private final UnitService unitService;

    private final Pbkdf2PasswordHash passwordHash;

    private final SecurityContext securityContext;

    @Inject
    public UserService(UserRepository repository, UnitService unitService, @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash, SecurityContext securityContext) {
        this.userRepository = repository;
        this.unitService = unitService;
        this.passwordHash = passwordHash;
        this.securityContext = securityContext;
    }
    @PermitAll
    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }
    @PermitAll
    public Optional<User> find(String name) {
        return userRepository.findByName(name);
    }
    @PermitAll
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PermitAll
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    @PermitAll
    public void update(User user) {
        userRepository.update(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        User user = userRepository.find(id).orElseThrow(NotFoundException::new);
        Optional<List<Unit>> unitsToDelete = unitService.findAllByUser(id);
        unitsToDelete.ifPresent(units -> units.forEach(unit -> {
            unitService.delete(unit.getId());
        }));
        userRepository.delete(user);

    }
    @PermitAll
    public void createAvatar(UUID id, InputStream avatar, String pathToAvatars) throws NotAllowedException {
        userRepository.find(id).ifPresent(user -> {
            try {
                Path destinationPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(destinationPath)) {
                    throw new NotAllowedException("Avatar already exists, to update avatar use PATCH method");
                }
                Files.copy(avatar, destinationPath);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

    @RolesAllowed(UserRoles.ADMIN)
    public void updateAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        userRepository.find(id).ifPresent(user -> {
            try {
                Path existingPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(existingPath)) {
                    Files.copy(avatar, existingPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new NotFoundException("User avatar not found, to create avatar use PUT method");
                }
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(employee -> passwordHash.verify(password.toCharArray(), employee.getPassword()))
                .orElse(false);
    }


    @PermitAll
    public void updateCallerPrincipalLastLoginDateTime() {
        findCallerPrincipal().ifPresent(principal -> principal.setLastLoginDateTime(LocalDateTime.now()));
    }

    public Optional<User> findCallerPrincipal() {
        if (securityContext.getCallerPrincipal() != null) {
            if (securityContext.isCallerInRole("admin")) {
                System.out.println("Użytkownik jest administratorem.");
            } else {
                System.out.println("Użytkownik nie jest administratorem.");
            }
            return find(securityContext.getCallerPrincipal().getName());
        } else {
            return Optional.empty();
        }
    }

}
