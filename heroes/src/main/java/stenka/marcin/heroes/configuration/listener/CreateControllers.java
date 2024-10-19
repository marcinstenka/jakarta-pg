package stenka.marcin.heroes.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import stenka.marcin.heroes.component.DtoFunctionFactory;
import stenka.marcin.heroes.fraction.controller.simple.FractionSimpleController;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.unit.controller.simple.UnitSimpleController;
import stenka.marcin.heroes.unit.service.UnitService;
import stenka.marcin.heroes.user.controller.simple.UserSimpleController;
import stenka.marcin.heroes.user.service.UserService;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");
        UnitService unitService = (UnitService) event.getServletContext().getAttribute("unitService");
        FractionService fractionService = (FractionService) event.getServletContext().getAttribute("fractionService");

        event.getServletContext().setAttribute("userController", new UserSimpleController(
                new DtoFunctionFactory(),
                userService
        ));

        event.getServletContext().setAttribute("unitController", new UnitSimpleController(
                unitService,
                userService,
                fractionService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("fractionController", new FractionSimpleController(
                fractionService,
                new DtoFunctionFactory()
        ));
    }

}
