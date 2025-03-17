package Actor;

import Model.Field;

public class Buyer extends Thread {
    private static int buyerCount = 1;

    private final int id = buyerCount++;
    private final Field field;
    private String threadName;

    public Buyer(Field field) {
        this.field = field;
    }

    @Override
    public void run() {
        threadName = Thread.currentThread().getName();
        field.buyAnimal(this.toString());
    }

    @Override
    public String toString() {
        return "Buyer %d (%s)".formatted(id, threadName);
    }
}
