package Actor;

import Model.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Service.TickHandler.getCurrTick;

public class BuyerHandler extends Thread {
    List<Field> fields;
    List<Buyer> buyerQueue = new ArrayList<>();
    Random spawnRate = new Random();
    private int lastTick = 0;

    public BuyerHandler(List<Field> fields) {
        this.fields = new ArrayList<>(fields);
    }

    private void spawnBuyer(int id) {
        int hit = 1;
        if (this.spawnRate.nextInt(10) == hit) {
            buyerQueue.add(new Buyer(id, this.fields));
        }
    }

    private boolean fieldsAreFree() {
        int busyFields = 0;
        for (Field field : fields) {
            if (field.buyerPresent) {
                busyFields += 1;
            }
        }

        return (busyFields == 5);
    }

    private void getNextAvailableBuyer() {
        if (!fieldsAreFree()) {
            return;
        }

        for (int i = 0; i < buyerQueue.size(); i++) {
            Buyer buyer = buyerQueue.get(i);
            if (!buyer.getFieldOfChoice().buyerPresent) {
                buyer.start();
                buyerQueue.remove(i);
                break;
            }
        }
    }

    @Override
    public void run() {
        int id = 0;
        while (true) {
            if(lastTick < getCurrTick()) {
                spawnBuyer(id);
                lastTick = getCurrTick();
            }

            getNextAvailableBuyer();

            // if buyer not in field
            // stack.pop().start()
        }
    }
}
