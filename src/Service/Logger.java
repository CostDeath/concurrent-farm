package Service;

import Model.AnimalType;

import java.util.List;

import static Service.TickLoopHandler.getCurrTick;

public abstract class Logger {
    public static void animalArrival(int amt, String list) {
        String log = "Tick %d\t| %d animals arrived in the enclosure. Current enclosure: %s";
        System.out.printf((log) + "%n", getCurrTick(), amt, list);
    }

    public static void animalCollection(int tick, int id, String thread, int amount, int ticks, List<AnimalType> inventory) {
        String log = "Tick %d\t| Farmer %d\t(%s) | Picked up %d animals (after %d ticks). Current Inventory: %s";
        System.out.printf((log) + "%n", tick, id, thread, amount, ticks, inventory.toString());
    }

    public static void animalDropOff(int tick, int id, String thread, int amount, AnimalType type, int ticks, List<AnimalType> inventory) {
        String log = "Tick %d\t| Farmer %d\t(%s) | Dropped off %dx%s (after %d ticks). Current Inventory: %s";
        System.out.printf((log) + "%n", tick, id, thread, amount, type, ticks, inventory.toString());
    }

    public static void travel(int tick, int id, String thread, String place, int ticks) {
        String log = "Tick %d\t| Farmer %d\t(%s) | Travelled to %s (after %d ticks)";
        System.out.printf((log) + "%n", tick, id, thread, place, ticks);
    }

    public static void sendToFields(int tick, int id, String thread, String animalType, int amount) {
        String log = "Tick %d\t| Farmer %d\t(%s) | added %d animals to %s field";
        System.out.printf((log) + "%n", tick, id, thread, amount, animalType);
    }

    public static void busyField(int tick, int id, String thread, String animalType) {
        String log = "Tick %d\t| Farmer %d\t(%s) | %s field is busy, moving to next field...";
        System.out.printf((log) + "%n", tick, id, thread, animalType);
    }

    public static void buyerBought(int tick, int id, String thread, int ticksWaited, String animalType) {
        String log = "Tick %d\t| Buyer %d\t(%s) | waited %d ticks and bought from %s field";
        System.out.printf((log) + "%n", tick, id, thread, ticksWaited, animalType);
    }

    public static void buyerAppeared(int tick, int id, String animalType) {
        String log = "Tick %d\t| Buyer %d has appeared and would like a %s";
        System.out.printf((log) + "%n", tick, id, animalType);
    }

    public static void buyerStarting(int tick, int id, String thread, String animalType) {
        String log = "Tick %d\t| Buyer %d\t(%s) | Buyer is now waiting at the field for %s";
        System.out.printf((log) + "%n", tick, id, thread, animalType);
    }
}
