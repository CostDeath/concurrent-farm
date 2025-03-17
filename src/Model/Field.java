package Model;

import Service.Logger;
import Service.TickEventHandler;

import java.util.List;

import static Service.PropertyManager.getProp;
import static Service.TickLoopHandler.getCurrTick;

public class Field {
    public static final int MAX_OCCUPANCY = getProp("max_field_occupancy");
    public static final int DROP_OFF_DELAY = getProp("field_drop_off_delay");
    public static final int MAX_BUYER_QUEUE = getProp("max_buyer_queue");

    private final AnimalType animalType;
    private int buyersWaiting = 0;
    private int currInterest = 0;
    private int currAmount = getProp("default_field_occupancy");

    public Field(AnimalType animalType) {
        this.animalType = animalType;
    }

    public synchronized void buyAnimal(String buyer, TickEventHandler tickEvent) {
        var start = getCurrTick();

        // Kill buyer if max buyers
        if (MAX_BUYER_QUEUE != 0 && buyersWaiting == MAX_BUYER_QUEUE) return;

        // Enter queue
        var positionInQueue = buyersWaiting;
        buyersWaiting++;
        Logger.buyerWaiting(buyer, animalType, positionInQueue);

        // Queue loop, 1 iteration each time you move forward
        while (positionInQueue != 0 || currAmount == 0) {
            try {
                if (positionInQueue != 0 && currAmount != 0) {
                    positionInQueue--;
                }
                wait();
                System.out.println("NOTIFIED");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Buy the animal
        currAmount--;
        buyersWaiting--;
        tickEvent.waitForEvent();

        Logger.buyerBought(buyer, animalType, start);
    }

    public synchronized void depositAnimals(String farmer, TickEventHandler event, List<AnimalType> inventory, int interest) {
        var start = getCurrTick();
        var count = 0;
        while (inventory.contains(animalType) && currAmount < MAX_OCCUPANCY) {
            event.waitTicks(DROP_OFF_DELAY);
            inventory.remove(animalType);
            currAmount++;
            count++;
        }
        currInterest -= interest;
        notifyAll();
        Logger.animalDeposit(farmer, count, animalType, start, currAmount);
    }

    public synchronized boolean isFull() {
        return currAmount >= MAX_OCCUPANCY;
    }

    public synchronized int getAvailableSlotCount() {
        return MAX_OCCUPANCY - currAmount;
    }

    public synchronized AnimalType getAnimalType() {
        return animalType;
    }

    public synchronized int getBuyerQueueSize() {
        return buyersWaiting;
    }

    public synchronized void showInterest(int amount) {
        currInterest += amount;
    }

    public synchronized int getInterest() {
        return currInterest;
    }
}
