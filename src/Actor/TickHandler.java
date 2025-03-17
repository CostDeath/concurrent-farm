package Actor;

import Model.AnimalType;
import Service.TickEventHandler;

import java.util.ArrayList;
import java.util.List;

import static Service.PropertyManager.getProp;

public class TickHandler extends Thread {
    private static final int ANIMAL_SPAWN_CHANCE = getProp("animal_spawn_chance");
    private static final int ANIMAL_SPAWN_AMOUNT = getProp("animal_spawn_amount");

    private final TickEventHandler event;

    public TickHandler(TickEventHandler event) {
        this.event = event;
    }

    @Override
    public void run() {
        attemptAnimalSpawn();
        attemptBuyerSpawn();
        event.triggerEvent();
    }

    private static void attemptAnimalSpawn() {
        // Spawn animals based on random chance
        if(Math.random() * ANIMAL_SPAWN_CHANCE < 1) {
            var allAnimals = AnimalType.values();
            var newAnimals = new ArrayList<>();
            for(int i = 0; i < ANIMAL_SPAWN_AMOUNT; i++) {
                var type = (int) (Math.random() * allAnimals.length - 1);
                newAnimals.add(allAnimals[type]);
            }
            //enclosure.addAll(generateRandomAnimals());
            //Logger.animalArrival(getCurrTick(), ANIMAL_SPAWN_AMOUNT, enclosure.toString());
        }
    }

    private static void attemptBuyerSpawn() {
        // return for now
    }
}
