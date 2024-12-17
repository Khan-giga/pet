package khan.pet.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Builder
@Setter
@Getter
@Entity
@Table(name = "workpieces")
@AllArgsConstructor
@NoArgsConstructor
public class Workpiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wood_types_id", nullable = false)
    private WoodType woodTypes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workpiece_diameter_id", nullable = false)
    private WorkpieceDiameter workpieceDiameter;

    @Column(name = "meters_length")
    private Long metersLength;

    @OneToMany(mappedBy = "workpieces", cascade = CascadeType.ALL)
    private Set<Board> boards = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Workpiece workpiece = (Workpiece) o;
        return getId() != null && Objects.equals(getId(), workpiece.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}