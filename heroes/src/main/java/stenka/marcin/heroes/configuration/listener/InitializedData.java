package stenka.marcin.heroes.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.service.UnitService;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.service.UserService;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {

    private UserService userService;

    private UnitService unitService;

    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        unitService = (UnitService) event.getServletContext().getAttribute("unitService");
        init();
    }

    @SneakyThrows
    private void init() {
        Unit dragon = Unit.builder()
                .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d320"))
                .name("dragon")
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

        userService.create(marcin);
        userService.create(oskar);
        userService.create(lukasz);
        userService.create(piotrek);

    }

}
