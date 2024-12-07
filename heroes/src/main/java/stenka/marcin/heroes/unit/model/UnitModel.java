package stenka.marcin.heroes.unit.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UnitModel {
    private UUID id;

    private String name;

    private int quantity;

    private Long version;

    private LocalDateTime creationDateTime;
}
