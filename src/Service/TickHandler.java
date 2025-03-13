package Service;

import Animals.AnimalType;

import java.util.ArrayList;
import java.util.List;

import static Animals.AnimalType.*;
import static Service.PropertyManager.getProp;

public abstract class TickHandler {
    private static final int MILLIS_PER_TICK = getProp("millis_per_tick");
    private static final int ANIMAL_SPAWN_CHANCE = getProp("animal_spawn_chance");
    private static final int ANIMAL_SPAWN_AMOUNT = getProp("animal_spawn_amount");
    private static int currTick = 0;

    public static void beginTickLoop(List<AnimalType> enclosure) throws InterruptedException {
        while(true) {
            Thread.sleep(MILLIS_PER_TICK);
            currTick++;
            tick(enclosure);
        }
    }

    public static synchronized int getCurrTick() {
        return currTick;
    }

    private static void tick(List<AnimalType> enclosure) {
        // Spawn animals based on random chance
        if(Math.random() * ANIMAL_SPAWN_CHANCE < 1) {
            enclosure.addAll(generateRandomAnimals());
            Logger.animalArrival(currTick, ANIMAL_SPAWN_AMOUNT, enclosure.toString());
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
