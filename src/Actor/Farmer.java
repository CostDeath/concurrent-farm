package Actor;

import Model.AnimalType;
import Model.Field;
import Service.Logger;

import java.util.ArrayList;
import java.util.List;

import static Actor.Enclosure.getAnimalsFromEnclosure;
import static Actor.Enclosure.isEnclosureEmpty;
import static Service.TickHandler.getCurrTick;

public class Farmer extends Thread {
    private final List<Field> fields;
    private final int id;
    private String threadName;

    // State Variables
    private List<AnimalType> inventory = new ArrayList<>();
    private AnimalType location;
    private int lastTick = 0;

    public Farmer(int id, List<Field> fields) {
        this.id = id;
        this.fields = new ArrayList<>(fields);
    }

    @Override
    public void run() {
        this.threadName = Thread.currentThread().getName();
        while(true) {
            // Execute code only on new tick
            if(lastTick < getCurrTick()) {
                if(inventory.size() < 10 && !isEnclosureEmpty()) getMoreAnimals();
                if(!inventory.isEmpty()) putAnimalsInFields();
                lastTick = getCurrTick();
            }
        }
    }

    private void getMoreAnimals() {
        moveTo(null); // Null location is the enclosure
        int start = getCurrTick();
        int amount = getAnimalsFromEnclosure(inventory);
        Logger.animalCollection(getCurrTick(), id, threadName, amount, getCurrTick() - start, inventory);
    }

    private void putAnimalsInFields() {}

    private void moveTo(AnimalType newLocation) {
        // Move only if needed
        if(location != newLocation) {
            int waitTick = getCurrTick() + 10;
            String placeName = newLocation == null ? "Enclosure" : newLocation.name();
            while(waitTick < getCurrTick()) {}
            location = newLocation;
            Logger.travel(getCurrTick(), id, threadName, placeName, 10);
        }
    }
}
