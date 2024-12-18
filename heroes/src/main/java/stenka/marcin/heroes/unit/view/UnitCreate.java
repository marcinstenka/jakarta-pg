package stenka.marcin.heroes.unit.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import stenka.marcin.heroes.component.ModelFunctionFactory;
import stenka.marcin.heroes.fraction.model.FractionModel;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitCreateModel;
import stenka.marcin.heroes.unit.model.UnitEditModel;
import stenka.marcin.heroes.unit.service.UnitService;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.service.UserService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class UnitCreate implements Serializable {

    private UnitService unitService;

    private final ModelFunctionFactory factory;

    private UserService userService;

    private FractionService fractionService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private UnitCreateModel unit;

    @Setter
    @Getter
    private List<FractionModel> fractions;

    private static final UUID TEMP_USER_ID = UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d310");

    @Inject
    public UnitCreate(ModelFunctionFactory factory) {
        this.factory = factory;
    }

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


    public void init() throws IOException {
        User tempUser = this.userService.find(TEMP_USER_ID).get();
        this.unit = UnitCreateModel.builder().id(UUID.randomUUID()).user(tempUser).build();
        this.fractions = fractionService.findAll().stream().map(factory.fractionToModel()).toList();
    }

    public String saveAction() {
        if (unit.getFraction() == null || unit.getName() == null) {
            return null;
        }
        unitService.create(factory.modelToUnit().apply(unit), TEMP_USER_ID, unit.getFraction().getId());
        return "/fraction/fraction_view.xhtml?faces-redirect=true&id=" + unit.getFraction().getId();

    }

}
