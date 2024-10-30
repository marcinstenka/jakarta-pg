package stenka.marcin.heroes.unit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "units")
public class Unit implements Serializable {
    @Id
    private UUID id;

    private String name;

    private int quantity;

    @ToString.Exclude
    @ManyToOne
    private Fraction fraction;

    @ToString.Exclude
    @ManyToOne
    private User user;
}
