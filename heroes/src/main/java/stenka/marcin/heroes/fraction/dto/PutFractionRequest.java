package stenka.marcin.heroes.fraction.dto;

import lombok.*;
import stenka.marcin.heroes.fraction.entity.FractionType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class PutFractionRequest {
    private String name;

    private FractionType fractionType;

}
