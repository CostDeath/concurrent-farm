package Service;

import Model.AnimalType;

import java.util.List;

import static Service.TickHandler.getCurrTick;

public abstract class Logger {
    public static void animalArrival(int amt, String list) {
        String log = "Tick %d\t| %d animals arrived in the enclosure. Current enclosure: %s";
        System.out.printf((log) + "%n", getCurrTick(), amt, list);
    }

    public static void animalCollection(String farmer, int amount, int ticks, List<AnimalType> inventory) {
        String log = "Tick %d\t| %s | Picked up %d animals (after %d ticks). Current Inventory: %s";
        System.out.printf((log) + "%n", getCurrTick(), farmer, amount, ticks, inventory.toString());
    }

    public static void travel(String farmer, String place, int ticks) {
        String log = "Tick %d\t| %s | Travelled to %s (after %d ticks)";
        System.out.printf((log) + "%n", getCurrTick(), farmer, place, ticks);
    }

    public static void sendToFields(String farmer, String animalType, int amount) {
        String log = "Tick %d\t| %s | added %d animals to %s field";
        System.out.printf((log) + "%n", getCurrTick(), farmer, animalType, amount);
    }

    public static void buyerBought(String buyer, int ticksWaited, String animalType) {
        String log = "Tick %d\t| %s | waited %d ticks and bought from %s field";
        System.out.printf((log) + "%n", getCurrTick(), buyer, animalType, ticksWaited);
    }

    public static void buyerAppeared(String buyer, String animalType) {
        String log = "Tick %d\t| %s appeared and wants %s";
        System.out.printf((log) + "%n", getCurrTick(), buyer, animalType);
    }
}
