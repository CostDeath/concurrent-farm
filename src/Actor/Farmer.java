package Actor;

import Model.AnimalType;
import Model.Field;
import Service.TickEventHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static Service.TickLoopHandler.getCurrTick;

public class Farmer extends Thread {

    private String threadName;
    private final int id;
    private final TickEventHandler tickEvent;
    private final ConcurrentMap<AnimalType, Field> fields;

    public Farmer(int id, TickEventHandler tickEvent, ConcurrentMap<AnimalType, Field> fields) {
        this.id = id;
        this.tickEvent = tickEvent;
        this.fields = fields;
    }

    @Override
    public void run() {
        this.threadName = Thread.currentThread().getName();
        while (true) {
            tickEvent.waitForEvent();
            System.out.println(threadName + " | " + getCurrTick());
        }
    }
}
