package Actor;

import Model.Field;
import Service.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Service.PropertyManager.getProp;
import static Service.TickHandler.getCurrTick;

public class BuyerHandler extends Thread {
    List<Field> fields;
    List<Buyer> buyerQueue = new ArrayList<>();
    Random spawnRate = new Random();
    private final static int SPAWN_CHANCE = getProp("buyer_spawn_chance");
    private int lastTick = 0;

    public BuyerHandler(List<Field> fields) {
        this.fields = new ArrayList<>(fields);
    }

    private int spawnBuyer(int id) {
        int hit = 0;
        if (this.spawnRate.nextInt(SPAWN_CHANCE) == hit) {
            buyerQueue.add(new Buyer(id, this.fields));
            Logger.buyerAppeared(getCurrTick(), id, buyerQueue.getLast().getFieldOfChoice().getAnimalType().name());
            id += 1;
        }
        return id;
    }

    private boolean fieldsAreBusy() {
        int busyFields = 0;
        for (Field field : fields) {
            if (field.isBuyerPresent()) {
                busyFields += 1;
            }
        }

        return (busyFields == 5);
    }

    private void getNextAvailableBuyer() {
        if (fieldsAreBusy()) {
            return;
        }

        for (int i = 0; i < buyerQueue.size(); i++) {
            Buyer buyer = buyerQueue.get(i);
            if (!buyer.getFieldOfChoice().isBuyerPresent()) {
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
                id = spawnBuyer(id);
                lastTick = getCurrTick();
            }

            getNextAvailableBuyer();

            // if buyer not in field
            // stack.pop().start()
        }
    }
}
