package Model;

import Service.Logger;
import Service.TickEventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Service.PropertyManager.getProp;
import static Service.TickLoopHandler.getCurrTick;

public abstract class Enclosure {
    private static final int MAX_OCCUPANCY = getProp("max_enclosure_occupancy");
    private static final int COLLECTION_DELAY = getProp("enclosure_collection_delay");
    private static final List<AnimalType> enclosure = Collections.synchronizedList(new ArrayList<>());

    public static synchronized void addAnimals(List<AnimalType> animals) {
        var added = animals.size();
        if (enclosure.size() + animals.size() <= MAX_OCCUPANCY || MAX_OCCUPANCY == 0) enclosure.addAll(animals);

            // If we can't fit all the animals, fit as many as possible
        else {
            added = 0;
            while (enclosure.size() < MAX_OCCUPANCY) {
                enclosure.add(animals.getLast());
                added++;
            }
        }
        if (added != 0) Logger.animalArrival(added, enclosure);
    }

    public static synchronized void pickUpAnimals(String farmer, TickEventHandler event, List<AnimalType> inventory, int cap) {
        var start = getCurrTick();
        var count = 0;
        while (inventory.size() < cap && !enclosure.isEmpty()) {
            event.waitTicks(COLLECTION_DELAY);
            inventory.add(enclosure.removeFirst());
            count++;
        }
        if (count != 0) Logger.animalCollection(farmer, count, start, inventory);
    }

    public static synchronized boolean isEmpty() {
        return enclosure.isEmpty();
    }
}
