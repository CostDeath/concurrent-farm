import Actor.BuyerHandler;
import Actor.Enclosure;
import Actor.Farmer;
import Model.AnimalType;
import Model.Field;

import java.util.Collections;
import java.util.List;

import static Service.PropertyManager.getProp;
import static Service.PropertyManager.loadProps;
import static Service.TickHandler.runTickLoop;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        loadProps();
        createActors();
        runTickLoop();
    }

    private static List<Field> createFields() {
        return Collections.synchronizedList(
                List.of(
                        new Field(AnimalType.Pig),
                        new Field(AnimalType.Cow),
                        new Field(AnimalType.Sheep),
                        new Field(AnimalType.Llama),
                        new Field(AnimalType.Chicken)
                )
        );
    }

    private static void createActors() {
        new Enclosure().start();
        List<Field> fields = createFields();
        int farmerAmount = getProp("farmer_amount");
        for (int i = 1; i <= farmerAmount; i++) {
            new Farmer(i, fields).start();
        }
        new BuyerHandler(fields).start();
    }
}