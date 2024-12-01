package stenka.marcin.heroes.user.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import stenka.marcin.heroes.component.ModelFunctionFactory;
import stenka.marcin.heroes.user.entity.User;
import stenka.marcin.heroes.user.model.UserModel;
import stenka.marcin.heroes.user.service.UserService;

import java.util.Optional;
import java.util.UUID;
@FacesConverter(value = "userConverter", forClass = UserModel.class, managed = true)
public class UserModelConverter implements Converter<UserModel> {
    private final UserService service;
    private final ModelFunctionFactory factory;
    @Inject
    public UserModelConverter(UserService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    @Override
    public UserModel getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("UserModelConverter.getAsObject");
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<User> user = service.find(UUID.fromString(value));
        System.out.println("UserModelConverter.getAsObject");
        return user.map(factory.userToModel()).orElse(null);
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, UserModel value) {
        System.out.println("UserModelConverter.getAsString");
        return value == null ? "" : value.getId().toString();
    }
}