package Animals;

public class Field {
    public static final int MAX_OCCUPANCY = 10;

    AnimalType animalType;
    int currAmount = 0;

    public Field(AnimalType animalType) {
        this.animalType = animalType;
    }
}
