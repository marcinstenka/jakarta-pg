package stenka.marcin.heroes.unit.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import stenka.marcin.heroes.component.ModelFunctionFactory;
import stenka.marcin.heroes.unit.model.UnitsModel;
import stenka.marcin.heroes.unit.service.UnitService;

@RequestScoped
@Named
public class UnitList {
    private UnitService unitService;

    private UnitsModel units;

    private final ModelFunctionFactory factory;

    @Inject
    public UnitList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    public UnitsModel getUnits() {
        if(units == null) {
            units = factory.unitsToModel().apply(unitService.findAllForCallerPrincipal());
        }
        return units;
    }

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public String deleteAction(UnitsModel.Unit unit){
        unitService.delete(unit.getId());
        return "unit_list?faces-redirect=true";
    }
}