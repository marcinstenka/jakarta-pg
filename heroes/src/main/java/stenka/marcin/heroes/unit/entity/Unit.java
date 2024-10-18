package stenka.marcin.heroes.unit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.user.entity.User;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(exclude = "fraction")
public class Unit implements Serializable {
    private UUID id;

    private String name;

    private int quantity;

    @ToString.Exclude
    private Fraction fraction;

    @ToString.Exclude
    private User user;
}
