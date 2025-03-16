package Model;

public class Field {
    public static final int MAX_OCCUPANCY = 10;
    private boolean buyerPresent = false;
    private boolean farmerPresent = false;

    AnimalType animalType;
    int currAmount = 0;

    public Field(AnimalType animalType) {
        this.animalType = animalType;
    }

    public synchronized AnimalType getAnimalType() {
        return this.animalType;
    }

    public synchronized void buyerPresent(boolean isPresent) {
        this.buyerPresent = isPresent;
    }

    public synchronized void farmerPresent(boolean isPresent) {
        this.farmerPresent = isPresent;
    }

    public synchronized boolean isBuyerPresent() {
        return this.buyerPresent;
    }

    public synchronized boolean isFarmerPresent() {
        return this.farmerPresent;
    }

    public synchronized void incrementAmount() {
        this.currAmount+= 1;
    }

    public synchronized void reduceAmount() {
        this.currAmount -= 1;
    }

    public synchronized int getAmount() {
        return this.currAmount;
    }
}
