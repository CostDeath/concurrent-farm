package People;

import Animals.AnimalType;
import Animals.Field;
import Service.Logger;

import java.util.ArrayList;
import java.util.List;

import static Service.TickHandler.getCurrTick;

public class Farmer extends Thread {
    private final List<Field> fields;
    private final List<AnimalType> enclosure;
    private final int id;
    private final String threadName;

    private List<AnimalType> inventory = new ArrayList<>();
    private AnimalType location;
    private int currTick = 0;

    public Farmer(int id, List<AnimalType> enclosure, List<Field> fields) {
        this.threadName = Thread.currentThread().getName();
        this.id = id;
        this.enclosure = enclosure;
        this.fields = new ArrayList<>(fields);
    }

    @Override
    public void run() {
        while(true) {
            // Execute code only on new tick
            if(currTick < getCurrTick()) {
                currTick = getCurrTick();
                System.out.println(id + " " + currTick);
                currTick = getCurrTick();
            }
//            if(inventory.size() < 10) {
//                if(atFields) {
//                    int waitTick = currTick + 10;
//                    while(waitTick < currTick) {}
//                    Logger.travel(currTick, id, "Enclosure", 10);
//                }
//                while(inventory.size() < 10 && !enclosure.isEmpty()) {
//                    inventory.add(enclosure.removeLast());
//                }
//            }
        }
    }

    private void move() {

    }
}
