import Actor.TestThread;
import Service.TickEventHandler;

import static Service.PropertyManager.loadProps;
import static Service.TickLoopHandler.runTickLoop;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        loadProps();
        var tickEvent = new TickEventHandler();
        new TestThread(tickEvent).start();
        new TestThread(tickEvent).start();
        runTickLoop(tickEvent);
    }
}