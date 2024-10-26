package stenka.marcin.heroes.unit.model;

import lombok.*;
import stenka.marcin.heroes.fraction.model.FractionModel;
import stenka.marcin.heroes.user.entity.User;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UnitCreateModel {
    private UUID id;

    private String name;

    private int quantity;

    private User user;

    private FractionModel fraction;
}
