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
import stenka.marcin.heroes.unit.entity.Unit;
import stenka.marcin.heroes.unit.model.UnitModel;
import stenka.marcin.heroes.unit.service.UnitService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class UnitView implements Serializable {
    private UnitService unitService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private UnitModel unit;

    @Inject
    public UnitView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void init() throws IOException {
        Optional<Unit> unit = unitService.find(id);
        if (unit.isPresent()) {
            this.unit = factory.unitToModel().apply(unit.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Unit not found!!!");
        }
    }


}
