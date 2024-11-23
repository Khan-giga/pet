package khan.pet.entity;

public class Blank {

    private String type;
    private int diameter;
    private int length;

    public Blank(String type, int diameter, int length) {
        this.type = type;
        this.diameter = diameter;
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
