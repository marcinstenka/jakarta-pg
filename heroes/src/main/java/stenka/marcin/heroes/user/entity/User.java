package stenka.marcin.heroes.user.entity;

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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(exclude = "units")
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    private UUID id;

    private String name;

    @ToString.Exclude
    private String password;

    @CollectionTable(name = "users__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Unit> units;

    private LocalDate accountCreation;
}
