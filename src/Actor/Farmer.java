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

    private synchronized void getMoreAnimals() {
        moveTo(null); // Null location is the enclosure
        int start = getCurrTick();
        int amount = getAnimalsFromEnclosure(inventory);
        Logger.animalCollection(getCurrTick(), id, threadName, amount, getCurrTick() - start, inventory);
    }

    private synchronized void putAnimalsInFields() {
        for (Field field : fields) {
            moveTo(field.getAnimalType());
            int amountAdded = 0;
            if (field.getAmount() < Field.MAX_OCCUPANCY && !field.isFarmerPresent()) { // if these conditions arent met, farmer moves on to next field
                field.farmerPresent(true);
                int i = 0;
                while (i < this.inventory.size()) {
                    if (this.inventory.get(i) != field.getAnimalType()) {
                        i += 1;
                    } else if (field.getAmount() == Field.MAX_OCCUPANCY) { // max cap filled
                        break;
                    } else {
                        field.incrementAmount();
                        this.inventory.remove(i); // animal has been moved to field
                        amountAdded += 1;
                    }
                }
                int lastTick = getCurrTick();
                Logger.sendToFields(getCurrTick(), this.id, this.threadName, field.getAnimalType().name(), amountAdded);
                while (lastTick == getCurrTick()) {} // making buyer wait 1 tick simulates the farmer finishing adding stock
                field.farmerPresent(false);
            }
            else {
                Logger.busyField(getCurrTick(), this.id, this.threadName, field.getAnimalType().name());
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
            Logger.travel(getCurrTick(), id, threadName, placeName,  getCurrTick() - start);
        }
    }
}
