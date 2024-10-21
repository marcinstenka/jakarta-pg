package stenka.marcin.heroes.fraction.model;

import lombok.*;
import stenka.marcin.heroes.fraction.entity.FractionType;
import stenka.marcin.heroes.unit.entity.Unit;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class FractionModel {
    private UUID id;

    private String name;

    private FractionType fractionType;

    @Singular
    private List<Unit> units;
}
