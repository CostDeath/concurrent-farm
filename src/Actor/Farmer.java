package Actor;

import Model.AnimalType;
import Model.Enclosure;
import Model.Field;
import Service.TickEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static Service.PropertyManager.getProp;
import static Service.TickLoopHandler.getCurrTick;

public class Farmer extends Thread {
    private static final int INVENTORY_CAP = getProp("farmer_inventory_size");
    private static final int MOVEMENT_DELAY = getProp("farmer_movement_delay");
    private static final int MOVEMENT_DELAY_PER_ANIMAL = getProp("farmer_movement_delay_per_animal");

    private final int id;
    private final TickEventHandler tickEvent;
    private final ConcurrentMap<AnimalType, Field> fields;
    private final List<AnimalType> inventory = new ArrayList<>();

    private String threadName;
    private AnimalType location;

    public Farmer(int id, TickEventHandler tickEvent, ConcurrentMap<AnimalType, Field> fields) {
        this.id = id;
        this.tickEvent = tickEvent;
        this.fields = fields;
    }

    @Override
    public void run() {
        threadName = Thread.currentThread().getName();
        while (true) {
            tickEvent.waitForEvent();
            attemptAnimalPickUp();
            // If inv not empty, go to fields
            // pick up all animals possible
            // Return point 1
            // Pick highest priority field
            // go to field & put animals
            // If inv not empty OR all fields busy, return to 1
            // wait for tick
        }
    }

    private void attemptAnimalPickUp() {
        if(Enclosure.isEmpty() || inventory.size() >= INVENTORY_CAP) return; // Do not get animals if inventory is full

        goTo(null); // Null field = enclosure
        Enclosure.pickUpAnimals(this.toString(), inventory, INVENTORY_CAP);
    }

    private void goTo(AnimalType field) {
        if(location == field) return; // Do not penalise if already at location

        tickEvent.waitForTick(getCurrTick() + MOVEMENT_DELAY + MOVEMENT_DELAY_PER_ANIMAL*inventory.size());
        location = field;
        System.out.println(this + " moved to " + location);
    }

    @Override
    public String toString() {
        return "Farmer %d (%s)".formatted(id, threadName);
    }
}
