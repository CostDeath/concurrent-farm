package Service;

import Actor.TickHandler;
import Model.AnimalType;
import Model.Field;

import java.util.concurrent.ConcurrentMap;

import static Service.PropertyManager.getProp;

public abstract class TickLoopHandler {
    private static final int MILLIS_PER_TICK = getProp("millis_per_tick");
    private static int currTick = 0;

    public static void runTickLoop(TickEventHandler event, ConcurrentMap<AnimalType, Field> fields) throws InterruptedException {
        while(true) {
            Thread.sleep(MILLIS_PER_TICK);
            currTick++;
            new TickHandler(event, fields).start();
        }
    }

    public static synchronized int getCurrTick() {
        return currTick;
    }
}
