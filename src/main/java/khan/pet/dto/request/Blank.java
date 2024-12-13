package khan.pet.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Blank {

    private String type;
    private Integer diameter;
    private Long length;

    public Blank(String type, Integer diameter, Long length) {
        this.type = type;
        this.diameter = diameter;
        this.length = length;
    }

}
