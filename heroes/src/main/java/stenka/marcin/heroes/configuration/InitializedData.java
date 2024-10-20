package stenka.marcin.heroes.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
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
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {

    private UserService userService;

    private UnitService unitService;

    private FractionService fractionService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(
            UserService userService,
            UnitService unitService,
            FractionService fractionService,
            RequestContextController requestContextController
    ) {
        this.userService = userService;
        this.unitService = unitService;
        this.fractionService = fractionService;
        this.requestContextController = requestContextController;
    }


    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }


    @SneakyThrows
    private void init() {
        requestContextController.activate();

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
                .build();

        User oskar = User.builder()
                .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d311"))
                .name("Oskar")
                .accountCreation(LocalDate.of(2021, Month.JULY, 2))
                .build();
        User lukasz = User.builder()
                .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d312"))
                .name("Lukasz")
                .accountCreation(LocalDate.of(2022, Month.MAY, 30))
                .build();
        User piotrek = User.builder()
                .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d313"))
                .name("Piotrek")
                .accountCreation(LocalDate.of(2024, Month.SEPTEMBER, 14))
                .build();

        Fraction castle = Fraction.builder()
                .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d330"))
                .name("Castle")
                .fractionType(FractionType.GOOD)
                .build();

        Fraction dungeon = Fraction.builder()
                .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d331"))
                .name("Dungeon")
                .fractionType(FractionType.EVIL)
                .build();


        blackDragon.setUser(marcin);
        blackDragon.setFraction(dungeon);

        archer.setUser(marcin);
        archer.setFraction(castle);

        cavalry.setUser(oskar);
        cavalry.setFraction(castle);

        marcin.setUnits(List.of(blackDragon, archer));
        oskar.setUnits(List.of(cavalry));

        castle.setUnits(List.of(archer, cavalry));
        dungeon.setUnits(List.of(blackDragon));

        userService.create(marcin);
        userService.create(oskar);
        userService.create(lukasz);
        userService.create(piotrek);

        unitService.create(blackDragon);
        unitService.create(archer);
        unitService.create(cavalry);

        fractionService.create(castle);
        fractionService.create(dungeon);

        requestContextController.deactivate();
    }

}
