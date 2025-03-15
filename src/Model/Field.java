package Model;

public class Field {
    public static final int MAX_OCCUPANCY = 10;
    public boolean buyerPresent = false;

    AnimalType animalType;
    int currAmount = 0;

    public Field(AnimalType animalType) {
        this.animalType = animalType;
    }

    public synchronized void buyerPresent(boolean isPresent) {
        this.buyerPresent = isPresent;
    }

    public synchronized void reduceAmount() {
        this.currAmount -= 1;
    }

    public synchronized int getAmount() {
        return this.currAmount;
    }
}
