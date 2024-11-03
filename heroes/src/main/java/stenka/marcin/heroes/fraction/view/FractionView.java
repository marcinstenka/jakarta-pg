package stenka.marcin.heroes.fraction.view;

import jakarta.ejb.EJB;
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
import stenka.marcin.heroes.unit.service.UnitService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class FractionView implements Serializable {
    private FractionService fractionService;

    private final ModelFunctionFactory factory;

    private UnitService unitService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private FractionModel fraction;

    @Inject
    public FractionView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setFractionService(FractionService fractionService) {
        this.fractionService = fractionService;
    }

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void init() throws IOException {
        Optional<Fraction> fraction = fractionService.find(id);
        if (fraction.isPresent()) {
            this.fraction = factory.fractionToModel().apply(fraction.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Fraction not found!!!");
        }
    }

    public String deleteUnit(UUID userId) {
        unitService.delete(userId);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
