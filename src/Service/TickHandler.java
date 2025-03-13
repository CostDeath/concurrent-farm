package Service;

import static Service.PropertyManager.getProp;

public abstract class TickHandler {
    private static final int MILLIS_PER_TICK = getProp("millis_per_tick");
    private static int currTick = 0;

    public static void runTickLoop() throws InterruptedException {
        while(true) {
            Thread.sleep(MILLIS_PER_TICK);
            currTick++;
        }
    }

    public static synchronized int getCurrTick() {
        return currTick;
    }
}
