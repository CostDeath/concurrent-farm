package Actor;

import Service.TickEventHandler;

import static Service.TickLoopHandler.getCurrTick;

public class TestThread extends Thread {

    private String threadName;
    private final TickEventHandler tickEvent;

    public TestThread(TickEventHandler tickEvent) {
        this.tickEvent = tickEvent;
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
