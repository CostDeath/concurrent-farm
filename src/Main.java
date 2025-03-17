import Actor.Farmer;
import Model.AnimalType;
import Model.Field;
import Service.TickEventHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static Service.PropertyManager.getProp;
import static Service.PropertyManager.loadProps;
import static Service.TickLoopHandler.runTickLoop;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        loadProps();
        var tickEvent = new TickEventHandler();
        var fields = createFields();
        createFarmers(tickEvent, fields);
        runTickLoop(tickEvent, fields);
    }

    private static void createFarmers(TickEventHandler tickEvent, ConcurrentMap<AnimalType, Field> fields) {
        var farmerAmount = getProp("farmer_amount");
        for (int i = 1; i <= farmerAmount; i++) {
            new Farmer(i, tickEvent, fields).start();
        }
    }

    private static ConcurrentMap<AnimalType, Field> createFields() {
        var map = new ConcurrentHashMap<AnimalType, Field>();
        // Create a field for each type of animal
        for (AnimalType type : AnimalType.values()) {
            map.put(type, new Field(type));
        }
        return map;
    }
}