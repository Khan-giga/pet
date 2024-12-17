package khan.pet.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "workpieces")
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

    @OneToMany(mappedBy = "workpieces")
    private Set<Board> boards = new LinkedHashSet<>();

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

    public WorkpieceDiameter getWorkpieceDiameter() {
        return workpieceDiameter;
    }

    public void setWorkpieceDiameter(WorkpieceDiameter workpieceDiameter) {
        this.workpieceDiameter = workpieceDiameter;
    }

    public Long getMetersLength() {
        return metersLength;
    }

    public void setMetersLength(Long metersLength) {
        this.metersLength = metersLength;
    }

    public Set<Board> getBoards() {
        return boards;
    }

    public void setBoards(Set<Board> boards) {
        this.boards = boards;
    }

}