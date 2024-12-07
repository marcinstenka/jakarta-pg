package stenka.marcin.heroes.unit.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.repository.api.UnitRepository;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.entity.UserRoles;
import stenka.marcin.heroes.user.repository.api.UserRepository;
import stenka.marcin.heroes.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UnitService {
    private final UnitRepository unitRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final FractionService fractionService;

    private final SecurityContext securityContext;

    @Inject
    public UnitService(UnitRepository unitRepository, UserRepository userRepository, UserService userService, FractionService fractionService,
                       @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.fractionService = fractionService;
        this.securityContext = securityContext;

    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Unit> find(UUID id) {
        return unitRepository.find(id);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Unit> find(User user, UUID id) {
        return unitRepository.findByIdAndUser(id, user);
    }


    @RolesAllowed(UserRoles.USER)
    public Optional<Unit> findForCallerPrincipal(UUID fractionId, UUID unitId) {
        checkAdminRoleOrOwner(unitRepository.find(unitId));
        return findByFractionAndUnit(fractionId, unitId);
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Unit> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return unitRepository.findAll();
        }
        User user = userRepository.findByName(securityContext.getCallerPrincipal().getName()).orElseThrow(IllegalStateException::new);
        return findAll(user);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Unit> findAll(User user) {
        return unitRepository.findAllByUser(user);
    }


    public Optional<Unit> findByFractionAndUnit(UUID fractionId, UUID unitId) {
        Fraction fraction = fractionService.find(fractionId)
                .orElseThrow(() -> new NotFoundException("Fraction not found: " + fractionId));

        return unitRepository.find(unitId)
                .filter(unit -> unit.getFraction().getId().equals(fraction.getId()));
    }

    @RolesAllowed(UserRoles.USER)
    public void create(Unit unit, UUID userId, UUID fractionId) {

        User user = userService.find(userId).orElseThrow(() -> new NotFoundException("User not found: " + userId));

        Fraction fraction = fractionService.find(fractionId).orElseThrow(() -> new NotFoundException("Fraction not found: " + fractionId));

        unitRepository.create(unit);

        List<Unit> userUnits = new ArrayList<>(user.getUnits());
        userUnits.add(unit);
        user.setUnits(userUnits);
        userService.update(user);

        List<Unit> fractionUnits = new ArrayList<>(fraction.getUnits());
        fractionUnits.add(unit);
        fraction.setUnits(fractionUnits);
        fractionService.update(fraction);
    }

    @RolesAllowed(UserRoles.USER)
    public void update(Unit unit, UUID initialFraction) {
        checkAdminRoleOrOwner(unitRepository.find(unit.getId()));

        User user = userService.find(unit.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found: " + unit.getUser().getId()));

        Fraction newFraction = fractionService.find(unit.getFraction().getId())
                .orElseThrow(() -> new NotFoundException("Fraction not found: " + unit.getFraction().getId()));

        if (!initialFraction.equals(newFraction.getId())) {
            Fraction oldFraction = fractionService.find(initialFraction)
                    .orElseThrow(() -> new NotFoundException("Initial fraction not found: " + initialFraction));

            oldFraction.getUnits().removeIf(oldFractionUnit -> oldFractionUnit.getId().equals(unit.getId()));
            fractionService.update(oldFraction);
        }

        boolean userUnitUpdated = user.getUnits().removeIf(userUnit -> userUnit.getId().equals(unit.getId()));
        if (userUnitUpdated) {
            user.getUnits().add(unit);
        } else {
            throw new NotFoundException("Unit not found in user's units: " + unit.getId());
        }

        newFraction.getUnits().removeIf(fractionUnit -> fractionUnit.getId().equals(unit.getId()));
        newFraction.getUnits().add(unit);

        userService.update(user);
        fractionService.update(newFraction);
        unitRepository.update(unit);
    }

    @RolesAllowed(UserRoles.USER)
    public void createForCallerPrincipal(Unit unit) {
        User user = userRepository.findByName(securityContext.getCallerPrincipal().getName()).orElseThrow(IllegalStateException::new);
        unit.setUser(user);
        unitRepository.create(unit);
    }

    @RolesAllowed(UserRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(unitRepository.find(id));

        Unit unit = unitRepository.find(id)
                .orElseThrow(NotFoundException::new);

        User user = userService.find(unit.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found: " + unit.getUser().getId()));

        Fraction fraction = fractionService.find(unit.getFraction().getId())
                .orElseThrow(() -> new NotFoundException("Fraction not found: " + unit.getFraction().getId()));

        user.getUnits().removeIf(u -> u.getId().equals(unit.getId()));
        fraction.getUnits().removeIf(f -> f.getId().equals(unit.getId()));

        userService.update(user);
        fractionService.update(fraction);
        unitRepository.delete(unit);
    }

    public Optional<List<Unit>> findAllByUser(UUID id) {
        return userService.find(id)
                .map(unitRepository::findAllByUser);
    }

    public Optional<List<Unit>> findAllByFraction(UUID id) {
        return fractionService.find(id)
                .map(unitRepository::findAllByFraction);
    }


    private void checkAdminRoleOrOwner(Optional<Unit> unit) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && unit.isPresent()
                && unit.get().getUser().getName().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
