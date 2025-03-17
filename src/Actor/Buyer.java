package Actor;

import Model.Field;
import Service.TickEventHandler;

public class Buyer extends Thread {
    private static int buyerCount = 1;

    private final int id = buyerCount++;
    private final Field field;
    private String threadName;
    private TickEventHandler tickEvent;

    public Buyer(Field field, TickEventHandler tickEvent) {
        this.field = field;
        this.tickEvent = tickEvent;
    }

    @Override
    public void run() {
        threadName = Thread.currentThread().getName();
        field.buyAnimal(this.toString(), tickEvent);
    }

    @Override
    public String toString() {
        return "Buyer %d (%s)".formatted(id, threadName);
    }
}
