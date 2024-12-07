package stenka.marcin.heroes.unit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import stenka.marcin.heroes.entity.VersionAndCreationDateAuditable;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.user.entity.User;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString()
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="units")
public class Unit extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID id;

    private String name;

    private int quantity;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Fraction fraction;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public void updateCreationDateTime()
    {
        super.updateCreationDateTime();
    }
    @Override
    public void updateEditDateTime()
    {
        super.updateEditDateTime();
    }
}
