package stenka.marcin.heroes.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.entity.FractionType;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.service.UserService;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
public class InitializedData {

    private UserService userService;

    private UnitService unitService;

    private FractionService fractionService;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    @EJB
    public void setFractionService(FractionService fractionService) {
        this.fractionService = fractionService;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userService.find("Marcin").isEmpty()) {
            Unit blackDragon = Unit.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d320"))
                    .name("Black dragon")
                    .quantity(3)
                    .build();
            Unit archer = Unit.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d321"))
                    .name("archer")
                    .quantity(22)
                    .build();
            Unit cavalry = Unit.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d322"))
                    .name("cavalry")
                    .quantity(8)
                    .build();


            User marcin = User.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d310"))
                    .name("Marcin")
                    .accountCreation(LocalDate.of(2020, Month.JANUARY, 8))
                    .units(Collections.emptyList())
                    .build();

            User oskar = User.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d311"))
                    .name("Oskar")
                    .accountCreation(LocalDate.of(2021, Month.JULY, 2))
                    .units(Collections.emptyList())
                    .build();
            User lukasz = User.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d312"))
                    .name("Lukasz")
                    .accountCreation(LocalDate.of(2022, Month.MAY, 30))
                    .units(Collections.emptyList())
                    .build();
            User piotrek = User.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d313"))
                    .name("Piotrek")
                    .accountCreation(LocalDate.of(2024, Month.SEPTEMBER, 14))
                    .units(Collections.emptyList())
                    .build();

            Fraction castle = Fraction.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d330"))
                    .name("Castle")
                    .fractionType(FractionType.GOOD)
                    .units(Collections.emptyList())
                    .build();

            Fraction dungeon = Fraction.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d331"))
                    .name("Dungeon")
                    .fractionType(FractionType.EVIL)
                    .units(Collections.emptyList())
                    .build();

            blackDragon.setUser(marcin);
            blackDragon.setFraction(dungeon);

            archer.setUser(marcin);
            archer.setFraction(castle);

            cavalry.setUser(oskar);
            cavalry.setFraction(castle);


            userService.create(marcin);
            userService.create(oskar);
            userService.create(lukasz);
            userService.create(piotrek);

            fractionService.create(castle);
            fractionService.create(dungeon);

            unitService.create(blackDragon, marcin.getId(), dungeon.getId());
            unitService.create(archer, marcin.getId(), castle.getId());
            unitService.create(cavalry, oskar.getId(), castle.getId());
        }
    }
}
