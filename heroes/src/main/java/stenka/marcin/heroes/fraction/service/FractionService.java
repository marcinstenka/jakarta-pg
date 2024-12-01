package stenka.marcin.heroes.fraction.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;
import stenka.marcin.heroes.user.entity.UserRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class FractionService {
    private final FractionRepository fractionRepository;

    private final UnitService unitService;

    private final SecurityContext securityContext;

    @Inject
    public FractionService(FractionRepository fractionRepository, UnitService unitService, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.fractionRepository = fractionRepository;
        this.unitService = unitService;
        this.securityContext = securityContext;

    }

    @RolesAllowed({UserRoles.ADMIN,UserRoles.USER})
    public Optional<Fraction> find(UUID id) {
        Optional<Fraction> fraction = fractionRepository.find(id);
        if (fraction.isPresent()) {
            List<Unit> units = fraction.get().getUnits();
            String currentUserName = securityContext.getCallerPrincipal().getName();
            boolean isAdmin = securityContext.isCallerInRole(UserRoles.ADMIN);
            System.out.println(units);
            units.removeIf(unit -> (!unit.getUser().getName().equals(currentUserName)) && !isAdmin);
            fraction.get().setUnits(units);
        }
        return fraction;
    }

    @RolesAllowed({UserRoles.ADMIN,UserRoles.USER})
    public List<Fraction> findAll() {
        return fractionRepository.findAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Fraction fraction) {
        fractionRepository.create(fraction);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(Fraction fraction) {
        fractionRepository.update(fraction);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        Fraction fraction = fractionRepository.find(id).orElseThrow(NotFoundException::new);
        Optional<List<Unit>> unitsToDelete = unitService.findAllByFraction(id);
        unitsToDelete.ifPresent(units -> units.forEach(unit -> {
            unitService.delete(unit.getId());
        }));
        fractionRepository.delete(fraction);
    }
}
