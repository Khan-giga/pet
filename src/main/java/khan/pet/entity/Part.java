package khan.pet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long partNumber;

    @ElementCollection
    @CollectionTable(name = "part_result_planks", joinColumns = @JoinColumn(referencedColumnName = "part_result_id"))
    @MapKeyColumn(name = "wood_type")
    @Column(name = "quantity")
    private Map<String, Long> planks;

}
