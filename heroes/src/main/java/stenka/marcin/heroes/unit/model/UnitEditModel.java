package stenka.marcin.heroes.unit.model;

import lombok.*;
import stenka.marcin.heroes.fraction.model.FractionModel;
import stenka.marcin.heroes.user.entity.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UnitEditModel {
    private String name;

    private int quantity;

    private User user;

    private FractionModel fraction;

    private Long version;
}
