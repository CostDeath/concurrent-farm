package Actor;

import Model.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Buyer extends Thread {
    private final List<Field> fields;
    private final int id;
    private final String threadName;
    private Field fieldOfChoice;
    private Random decisionMaker = new Random();

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

    @Override
    public void run() {
        while (this.fieldOfChoice.getAmount() == 0) {}

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
