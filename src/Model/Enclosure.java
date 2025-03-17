package Model;

import Service.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Service.PropertyManager.getProp;

public abstract class Enclosure {
    private static final int MAX_OCCUPANCY = getProp("max_enclosure_occupancy");
    private static final List<AnimalType> enclosure = Collections.synchronizedList(new ArrayList<>());

    public static synchronized void addAnimals(List<AnimalType> animals) {
        var added = animals.size();
        if(enclosure.size() + animals.size() <= MAX_OCCUPANCY || MAX_OCCUPANCY == 0) enclosure.addAll(animals);

        // If we can't fit all the animals, fit as many as possible
        else {
            added = 0;
            while(enclosure.size() < MAX_OCCUPANCY) {
                enclosure.add(animals.getLast());
                added++;
            }
        }
        if(added != 0) Logger.animalArrival(added, enclosure.toString());
    }

    public static synchronized void pickUpAnimals(String farmer, List<AnimalType> inventory, int cap) {
        int count = 0;
        while(inventory.size() < cap && !enclosure.isEmpty()) {
            inventory.add(enclosure.removeFirst());
            count++;
        }
        System.out.println(farmer + " picked up " + count + "animals from the enclosure");
    }

    public static synchronized boolean isEmpty() {
        return enclosure.isEmpty();
    }
}
