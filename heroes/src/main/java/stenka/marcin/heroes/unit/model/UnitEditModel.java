package stenka.marcin.heroes.unit.model;

import lombok.*;

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
}
