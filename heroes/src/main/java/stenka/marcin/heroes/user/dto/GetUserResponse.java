package stenka.marcin.heroes.user.dto;


import lombok.*;
import stenka.marcin.heroes.fraction.dto.GetFractionResponse;


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

public class GetUserResponse {
    private UUID id;

    private String name;

    private LocalDate accountCreation;

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
    private List<GetFractionResponse.Unit> units;
}
