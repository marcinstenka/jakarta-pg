package stenka.marcin.heroes.fraction.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import stenka.marcin.heroes.component.ModelFunctionFactory;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.model.FractionModel;
import stenka.marcin.heroes.fraction.model.FractionsModel;
import stenka.marcin.heroes.fraction.service.FractionService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = FractionsModel.class, managed = true)
public class FractionModelConverter implements Converter<FractionModel> {
    private final FractionService fractionService;

    private final ModelFunctionFactory factory;

    @Inject
    public FractionModelConverter(FractionService fractionService, ModelFunctionFactory factory) {
        this.fractionService = fractionService;
        this.factory = factory;
    }

    @Override
    public FractionModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Fraction> fraction = fractionService.find(UUID.fromString(value));
        return fraction.map(factory.fractionToModel()).orElse(null);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, FractionModel value) {
        return value == null ? "" : value.getId().toString();


    }
}
