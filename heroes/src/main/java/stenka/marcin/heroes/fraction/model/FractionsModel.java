package stenka.marcin.heroes.fraction.model;

import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class FractionsModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Fraction {
        private UUID id;
        private String name;
    }

    @Singular
    private List<Fraction> fractions;

}
