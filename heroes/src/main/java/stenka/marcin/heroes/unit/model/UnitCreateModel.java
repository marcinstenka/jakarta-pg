package stenka.marcin.heroes.unit.model;

import lombok.*;
import stenka.marcin.heroes.fraction.model.FractionModel;
import stenka.marcin.heroes.user.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    @NotBlank
    private String name;

    @Min(1)
    private int quantity;
    @NotNull
    private User user;
    @NotNull
    private FractionModel fraction;
}
