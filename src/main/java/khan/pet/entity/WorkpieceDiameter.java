package khan.pet.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "workpiece_diameter")
public class WorkpieceDiameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "milimeters_diameter")
    private Integer milimetersDiameter;

    @Column(name = "board_count")
    private Integer boardCount;

    @OneToMany(mappedBy = "workpieceDiameter")
    private Set<Workpiece> workpieces = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMilimetersDiameter() {
        return milimetersDiameter;
    }

    public void setMilimetersDiameter(Integer milimetersDiameter) {
        this.milimetersDiameter = milimetersDiameter;
    }

    public Integer getBoardCount() {
        return boardCount;
    }

    public void setBoardCount(Integer boardCount) {
        this.boardCount = boardCount;
    }

    public Set<Workpiece> getWorkpieces() {
        return workpieces;
    }

    public void setWorkpieces(Set<Workpiece> workpieces) {
        this.workpieces = workpieces;
    }

}