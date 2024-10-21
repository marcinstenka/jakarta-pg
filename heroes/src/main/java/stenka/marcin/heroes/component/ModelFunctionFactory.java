package stenka.marcin.heroes.component;

import jakarta.enterprise.context.ApplicationScoped;
import stenka.marcin.heroes.fraction.model.function.FractionToEditModelFunction;
import stenka.marcin.heroes.fraction.model.function.FractionToModelFunction;
import stenka.marcin.heroes.fraction.model.function.FractionsToModelFunction;
import stenka.marcin.heroes.fraction.model.function.ModelToFractionFunction;

@ApplicationScoped
public class ModelFunctionFactory {

    public FractionToModelFunction fractionToModel() {
        return new FractionToModelFunction();
    }

    public FractionsToModelFunction fractionsToModel() {
        return new FractionsToModelFunction();
    }

    public FractionToEditModelFunction fractionToEditModel() {
        return new FractionToEditModelFunction();
    }

    public ModelToFractionFunction modelToFraction() {
        return new ModelToFractionFunction();
    }
}
