package stenka.marcin.heroes.fraction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import lombok.experimental.SuperBuilder;
import stenka.marcin.heroes.unit.entity.Unit;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(exclude = "units")
@Entity
@Table(name = "fractions")
public class Fraction implements Serializable {
    @Id
    private UUID id;

    private String name;

    private FractionType fractionType;


    @ToString.Exclude
    @OneToMany(mappedBy = "fraction", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Unit> units;
}
