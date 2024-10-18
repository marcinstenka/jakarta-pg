package stenka.marcin.heroes.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import stenka.marcin.heroes.dataStore.DataStore;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;
import stenka.marcin.heroes.fraction.repository.memory.FractionInMemoryRepository;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.unit.repository.api.UnitRepository;
import stenka.marcin.heroes.unit.repository.memory.UnitInMemoryRepository;
import stenka.marcin.heroes.unit.service.UnitService;
import stenka.marcin.heroes.user.repository.api.UserRepository;
import stenka.marcin.heroes.user.repository.memory.UserInMemoryRepository;
import stenka.marcin.heroes.user.service.UserService;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);
        UnitRepository unitRepository = new UnitInMemoryRepository(dataSource);
        FractionRepository fractionRepository = new FractionInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("userService", new UserService(userRepository));
        event.getServletContext().setAttribute("unitService", new UnitService(unitRepository, userRepository));
        event.getServletContext().setAttribute("fractionService", new FractionService(fractionRepository));
    }

}
