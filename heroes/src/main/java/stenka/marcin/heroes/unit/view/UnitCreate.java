package stenka.marcin.heroes.unit.view;

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

    private final UnitService unitService;

    private final ModelFunctionFactory factory;

    private final UserService userService;

    private final FractionService fractionService;

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
    public UnitCreate(UnitService unitService, ModelFunctionFactory factory, UserService userService, FractionService fractionService) {
        this.unitService = unitService;
        this.factory = factory;
        this.userService = userService;
        this.fractionService = fractionService;
    }

    public void init() throws IOException {
        User tempUser = this.userService.find(TEMP_USER_ID).get();
        this.unit = UnitCreateModel.builder().id(UUID.randomUUID()).user(tempUser).build();
        this.fractions = fractionService.findAll().stream().map(factory.fractionToModel()).toList();
    }

    public String saveAction() {
        unitService.create(factory.modelToUnit().apply(unit), TEMP_USER_ID, unit.getFraction().getId());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
