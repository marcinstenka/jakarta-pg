package stenka.marcin.heroes.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
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

@WebListener
public class InitializedData implements ServletContextListener {

    private UserService userService;

    private UnitService unitService;

    private FractionService fractionService;

    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        unitService = (UnitService) event.getServletContext().getAttribute("unitService");
        fractionService = (FractionService) event.getServletContext().getAttribute("fractionService");
        init();
    }

    @SneakyThrows
    private void init() {
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
    }

}
