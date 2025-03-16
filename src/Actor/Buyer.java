package Actor;

import Model.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Service.TickHandler.getCurrTick;

public class Buyer extends Thread {
    private final List<Field> fields;
    private final int id;
    private final String threadName;
    private Field fieldOfChoice;
    private final Random decisionMaker = new Random();

    public Buyer(int id, List<Field> fields) {
        this.id = id;
        this.threadName = Thread.currentThread().getName();
        this.fields = new ArrayList<>(fields);
        chooseRandomField();
    }

    public void chooseRandomField() {
       int choice = this.decisionMaker.nextInt(5);
       this.fieldOfChoice = this.fields.get(choice);
    }

    public Field getFieldOfChoice() {
        return this.fieldOfChoice;
    }

    @Override
    public void run() {
        this.fieldOfChoice.buyerPresent(true);
        int ticksWaited = 0;
        int lastTick = getCurrTick();
        while (this.fieldOfChoice.getAmount() == 0) {
            if (lastTick < getCurrTick()) {
                ticksWaited += 1; // ticks waited
            }
        }

        // buyer bought an animal
        this.fieldOfChoice.reduceAmount();

        // buyer leaves
        this.fieldOfChoice.buyerPresent(false);


        // Functionality Here
        /*
        IDEA:
        - Use random function to buy an animal from one of the fields
        - Check to see if another buyer is in the field and if field is empty
        - If field is empty, buyer will wait
        - Once selected field is filled, it will buy
         */
    }
}
