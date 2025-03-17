package Service;

import static Service.TickLoopHandler.getCurrTick;

public class TickEventHandler {
    public synchronized void waitForEvent() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void waitForTick(int tick) {
        try {
            while (tick != getCurrTick()) wait();
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void triggerEvent() {
        notifyAll(); // Notify all waiting threads
    }
}
