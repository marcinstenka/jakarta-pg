package stenka.marcin.heroes.fraction.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import stenka.marcin.heroes.component.ModelFunctionFactory;
import stenka.marcin.heroes.fraction.model.FractionsModel;
import stenka.marcin.heroes.fraction.service.FractionService;

@RequestScoped
@Named
public class FractionList {
    private FractionService fractionService;

    private final ModelFunctionFactory factory;

    private FractionsModel fractions;


    @Inject
    public FractionList(ModelFunctionFactory factory) {
        this.factory = factory;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
    }


    @EJB
    public void setFractionService(FractionService fractionService) {
        this.fractionService = fractionService;
    }

    public FractionsModel getFractions() {
        if (fractions == null) {
            fractions = factory.fractionsToModel().apply(fractionService.findAll());
        }
        return fractions;
    }

    public String deleteAction(FractionsModel.Fraction fraction) {
        fractionService.delete(fraction.getId());
        return "fraction_list?faces-redirect=true";
    }

}
