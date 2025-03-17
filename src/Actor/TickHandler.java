package Actor;

import Model.AnimalType;
import Model.Enclosure;
import Model.Field;
import Service.TickEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static Service.PropertyManager.getProp;

public class TickHandler extends Thread {
    private static final int ANIMAL_SPAWN_CHANCE = getProp("animal_spawn_chance");
    private static final int ANIMAL_SPAWN_AMOUNT = getProp("animal_spawn_amount");
    private static final int BUYER_SPAWN_CHANCE = getProp("buyer_spawn_chance");
    private static final int BUYER_SPAWN_AMOUNT = getProp("buyer_spawn_amount");

    private final TickEventHandler event;
    private final ConcurrentMap<AnimalType, Field> fields;

    public TickHandler(TickEventHandler event, ConcurrentMap<AnimalType, Field> fields) {
        this.event = event;
        this.fields = fields;
    }

    @Override
    public void run() {
        attemptAnimalSpawn();
        attemptBuyerSpawn();
        event.triggerEvent();
    }

    private void attemptAnimalSpawn() {
        // Spawn animals based on random chance
        if(Math.random() * ANIMAL_SPAWN_CHANCE < 1) {
            var allAnimals = AnimalType.values();
            var newAnimals = new ArrayList<AnimalType>();

            for(int i = 0; i < ANIMAL_SPAWN_AMOUNT; i++) {
                // Generate rand int between 0 and amount of animals
                var type = (int) (Math.random() * allAnimals.length - 1);
                newAnimals.add(allAnimals[type]); // add animal corresponding to int
            }
            Enclosure.addAnimals(newAnimals);
        }
    }

    private void attemptBuyerSpawn() {
        // Spawn buyers based on random chance
        if(Math.random() * BUYER_SPAWN_CHANCE < 1) {
            var allFields = AnimalType.values();

            for(int i = 0; i < BUYER_SPAWN_AMOUNT; i++) {
                // Generate rand int between 0 and amount of animals
                var type = (int) (Math.random() * allFields.length - 1);
                new Buyer(fields.get(allFields[type])).start(); // create buyer of corresponding field
            }
        }
    }
}
