package stenka.marcin.heroes.unit.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import stenka.marcin.heroes.component.ModelFunctionFactory;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.model.FractionModel;
import stenka.marcin.heroes.fraction.service.FractionService;
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitEditModel;
import stenka.marcin.heroes.unit.service.UnitService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class UnitEdit implements Serializable {

    private final UnitService unitService;

    private final ModelFunctionFactory factory;

    private final FractionService fractionService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private UnitEditModel unit;

    @Setter
    @Getter
    private UUID initialFraction;

    @Setter
    @Getter
    private List<FractionModel> fractions;

    @Inject
    public UnitEdit(UnitService unitService, ModelFunctionFactory factory, FractionService fractionService) {
        this.unitService = unitService;
        this.factory = factory;
        this.fractionService = fractionService;
    }

    public void init() throws IOException {
        Optional<Unit> unit = unitService.find(id);
        if (unit.isPresent()) {
            this.unit = factory.unitToEditModel().apply(unit.get());
            this.initialFraction = this.unit.getFraction().getId();
            this.fractions = fractionService.findAll().stream().map(factory.fractionToModel()).toList();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Unit not found!!!");
        }
    }

    public String saveAction() {
        unitService.update(factory.updateUnit().apply(unitService.find(id).orElseThrow(), unit), initialFraction);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
