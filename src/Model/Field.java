package Model;

import java.util.List;

import static Service.PropertyManager.getProp;
import static Service.TickHandler.getCurrTick;

public class Field {
    public static final int MAX_OCCUPANCY = getProp("max_field_occupancy");
    public static final int DROP_OFF_DELAY = getProp("field_drop_off_delay");

    private final AnimalType animalType;
    private int currAmount = 0;

    public Field(AnimalType animalType) {
        this.animalType = animalType;
    }

    public synchronized boolean isFull() {
        return currAmount >= MAX_OCCUPANCY;
    }

    public synchronized AnimalType getAnimalType() {
        return animalType;
    }

    public synchronized int putAnimalsInField(List<AnimalType> inventory) {
        int amount = 0; // Keep track of how many animals are put

        while(inventory.contains(animalType) && currAmount < MAX_OCCUPANCY) {
            // Set the delay
            int waitTick = getCurrTick() + DROP_OFF_DELAY;

            inventory.remove(animalType);
            currAmount++;

            while(waitTick > getCurrTick()) {}
            amount++;
        }
        return amount;
    }
}
