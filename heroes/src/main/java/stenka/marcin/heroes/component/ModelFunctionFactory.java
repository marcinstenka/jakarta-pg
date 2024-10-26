package stenka.marcin.heroes.component;

import jakarta.enterprise.context.ApplicationScoped;
import stenka.marcin.heroes.fraction.model.function.FractionToEditModelFunction;
import stenka.marcin.heroes.fraction.model.function.FractionToModelFunction;
import stenka.marcin.heroes.fraction.model.function.FractionsToModelFunction;
import stenka.marcin.heroes.fraction.model.function.ModelToFractionFunction;
import stenka.marcin.heroes.unit.dto.function.UpdateUnitWithModelFunction;
import stenka.marcin.heroes.unit.model.function.ModelToUnitFunction;
import stenka.marcin.heroes.unit.model.function.UnitToEditModelFunction;
import stenka.marcin.heroes.unit.model.function.UnitToModelFunction;

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

    public UnitToModelFunction unitToModel() {
        return new UnitToModelFunction();
    }

    public UnitToEditModelFunction unitToEditModel() {
        return new UnitToEditModelFunction();
    }

    public ModelToUnitFunction modelToUnit() {
        return new ModelToUnitFunction();
    }

    public UpdateUnitWithModelFunction updateUnit() {
        return new UpdateUnitWithModelFunction();
    }
}
