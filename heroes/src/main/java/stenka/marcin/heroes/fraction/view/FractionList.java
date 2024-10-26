package stenka.marcin.heroes.fraction.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import stenka.marcin.heroes.component.ModelFunctionFactory;
import stenka.marcin.heroes.fraction.model.FractionsModel;
import stenka.marcin.heroes.fraction.service.FractionService;

@RequestScoped
@Named
public class FractionList {
    private final FractionService fractionService;

    private final ModelFunctionFactory factory;

    private FractionsModel fractions;


    @Inject
    public FractionList(FractionService fractionService, ModelFunctionFactory factory) {
        this.fractionService = fractionService;
        this.factory = factory;
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
