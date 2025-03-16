package Actor;

import Model.AnimalType;
import Model.Field;
import Service.Logger;

import java.util.ArrayList;
import java.util.List;

import static Actor.Enclosure.getAnimalsFromEnclosure;
import static Actor.Enclosure.isEnclosureEmpty;
import static Service.PropertyManager.getProp;
import static Service.TickHandler.getCurrTick;

public class Farmer extends Thread {
    private static final int INVENTORY_CAP = getProp("farmer_inventory_size");
    private static final int MOVEMENT_DELAY = getProp("farmer_movement_delay");
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
                if(inventory.size() < INVENTORY_CAP && !isEnclosureEmpty()) getMoreAnimals();
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

    private void putAnimalsInFields() {
        for (Field field : fields) {
            moveTo(field.getAnimalType());
            if (field.getAmount() < Field.MAX_OCCUPANCY && !field.isFarmerPresent()) { // if these conditions arent met, farmer moves on to next field
                field.farmerPresent(true);
                int i = 0;
                while (i < inventory.size()) {
                    if (inventory.get(i) != field.getAnimalType()) {
                        i += 1;
                    } else if (field.getAmount() == Field.MAX_OCCUPANCY) { // max cap filled
                        break;
                    } else {
                        field.incrementAmount();
                        inventory.remove(i); // animal has been moved to field
                    }
                }
                field.farmerPresent(false);
            }
        }
    }

    private void moveTo(AnimalType newLocation) {
        // Move only if needed
        if(location != newLocation) {
            int start = getCurrTick();
            int waitTick = getCurrTick() + MOVEMENT_DELAY;
            String placeName = newLocation == null ? "Enclosure" : newLocation.name();
            while(waitTick > getCurrTick()) {}
            location = newLocation;
            Logger.travel(getCurrTick(), id, threadName, placeName, start - getCurrTick());
        }
    }
}
