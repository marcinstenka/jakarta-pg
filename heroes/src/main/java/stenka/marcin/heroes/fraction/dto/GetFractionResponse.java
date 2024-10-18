package stenka.marcin.heroes.fraction.dto;


import lombok.*;
import stenka.marcin.heroes.fraction.entity.FractionType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetFractionResponse {
    private UUID id;

    private String name;

    private LocalDate accountCreation;

    private FractionType fractionType;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Unit {
        private UUID id;

        private String name;
    }

    @Singular
    private List<Unit> units;

}
