package Actor;

import Model.AnimalType;
import Service.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static Model.AnimalType.*;
import static Service.PropertyManager.getProp;
import static Service.TickHandler.getCurrTick;

public class Enclosure extends Thread {
    private static final int ANIMAL_SPAWN_CHANCE = getProp("animal_spawn_chance");
    private static final int ANIMAL_SPAWN_AMOUNT = getProp("animal_spawn_amount");
    private static final int ENCLOSURE_COLLECTION_DELAY = getProp("enclosure_collection_delay");
    private static final int FARMER_INVENTORY_CAP =  getProp("farmer_inventory_size");
    private static final List<AnimalType> enclosure = Collections.synchronizedList(new ArrayList<>());
    private int lastTick = 0;
    private final Random spawner = new Random();

    public static synchronized boolean isEnclosureEmpty() {
        return enclosure.isEmpty();
    }

    public static synchronized int getAnimalsFromEnclosure(List<AnimalType> inventory) {
        int amount = 0; // Keep track of how many animals are collected

        while(inventory.size() < FARMER_INVENTORY_CAP && !enclosure.isEmpty()) {
            // Go through the collection delay
            int waitTick = getCurrTick() + ENCLOSURE_COLLECTION_DELAY;
            while(waitTick > getCurrTick()) {}

            inventory.add(enclosure.removeLast());
            amount++;
        }
        return amount;
    }

    @Override
    public void run() {
        while(true) {
            // Execute code only on new tick
            if(lastTick < getCurrTick()) {
                // Spawn animals based on random chance
                if(this.spawner.nextInt(ANIMAL_SPAWN_CHANCE) == 0) {
                    enclosure.addAll(generateRandomAnimals());
                    Logger.animalArrival(getCurrTick(), ANIMAL_SPAWN_AMOUNT, enclosure.toString());
                }
                lastTick = getCurrTick();
            }
        }
    }

    private static List<AnimalType> generateRandomAnimals() {
        List<AnimalType> animals = new ArrayList<>();
        for(int i = 0; i < ANIMAL_SPAWN_AMOUNT; i++) {
            animals.add(switch((int) (Math.random() * 5)) {
                case 1 -> Pig;
                case 2 -> Cow;
                case 3 -> Sheep;
                case 4 -> Llama;
                default -> Chicken;
            });
        }
        return animals;
    }
}
