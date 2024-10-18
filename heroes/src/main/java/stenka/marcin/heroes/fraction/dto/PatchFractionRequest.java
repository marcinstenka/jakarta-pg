package stenka.marcin.heroes.fraction.dto;

import lombok.*;
import stenka.marcin.heroes.fraction.entity.FractionType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class PatchFractionRequest {
    private String name;

    private FractionType fractionType;

}
