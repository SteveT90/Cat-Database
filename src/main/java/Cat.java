import java.sql.Date;

/**
 * The Cat class is used to create cat objects and store the cats information.
 */

public class Cat {
    private String name;
    private Date dob;
    private String breed;
    private int weight;
    private String color;

    public Cat() {
    }

    public Cat(String name, Date dob, String breed, int weight, String color) {
        this.name = name;
        this.dob = dob;
        this.breed = breed;
        this.weight = weight;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
