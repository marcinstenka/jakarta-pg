package stenka.marcin.heroes.user.view;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import stenka.marcin.heroes.component.ModelFunctionFactory;
import stenka.marcin.heroes.user.model.UsersModel;
import stenka.marcin.heroes.user.service.UserService;

@RequestScoped
@Named
@NoArgsConstructor(force = true)
public class UserList {
    private final UserService service;

    private UsersModel users;

    private final ModelFunctionFactory factory;

    public UserList(UserService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    public UsersModel getUsers() {
        if (users == null) {
            users = factory.usersToModel().apply(service.findAll());
        }
        return users;
    }
    public String deleteAction(UsersModel.User user) {
        service.delete(user.getId());
        return "user_list?faces-redirect=true";
    }
}