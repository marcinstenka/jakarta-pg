package stenka.marcin.heroes.unit.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UnitsModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Unit {

        private UUID id;

        private int quantity;

        private String name;

        private Long version;

        private LocalDateTime creationDateTime;

        private LocalDateTime modifiedDateTime;
    }

    @Singular
    private List<Unit> units;

}