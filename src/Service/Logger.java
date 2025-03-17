package Service;

import Model.AnimalType;

import java.util.List;

public abstract class Logger {
    public static void animalArrival(int tick, int amt, String list) {
        String log = "Tick %d\t| %d animals arrived in the enclosure. Current enclosure: %s";
        System.out.printf((log) + "%n", tick, amt, list);
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
}
