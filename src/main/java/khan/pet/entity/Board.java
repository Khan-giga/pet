package khan.pet.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wood_types_id", nullable = false)
    private WoodType woodTypes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workpieces_id", nullable = false)
    private Workpiece workpieces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WoodType getWoodTypes() {
        return woodTypes;
    }

    public void setWoodTypes(WoodType woodTypes) {
        this.woodTypes = woodTypes;
    }

    public Workpiece getWorkpieces() {
        return workpieces;
    }

    public void setWorkpieces(Workpiece workpieces) {
        this.workpieces = workpieces;
    }

}