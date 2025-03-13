import Animals.AnimalType;
import Animals.Field;
import People.Farmer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Service.PropertyManager.getProp;
import static Service.PropertyManager.loadProps;
import static Service.TickHandler.beginTickLoop;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        loadProps();
        List<AnimalType> enclosure = Collections.synchronizedList(new ArrayList<>());
        createFarmers(enclosure);
        beginTickLoop(enclosure);
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

    private static void createFarmers(List<AnimalType> enclosure) {
        List<Field> fields = createFields();
        for(int i = 1; i <= getProp("farmer_amount"); i++) {
            new Farmer(i, enclosure, fields).start();
        }
    }
}