package Actor;

import Service.TickEventHandler;

public class TickHandler extends Thread {
    private final TickEventHandler event;

    public TickHandler(TickEventHandler event) {
        this.event = event;
    }

    @Override
    public void run() {
        event.triggerEvent();
    }
}
