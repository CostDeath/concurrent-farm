package Service;

import Model.AnimalType;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static Service.TickLoopHandler.getCurrTick;

public abstract class Logger {
    public static void animalArrival(int amount, List<AnimalType> enclosure) {
        var log = "%s %d animals arrived in the enclosure. Current enclosure: %s";
        System.out.printf((log) + "%n", getTick(), amount, formatList(enclosure));
    }

    public static void animalCollection(String farmer, int amount, int start, List<AnimalType> inventory) {
        String log = "%s %s picked up %d animals (After %d ticks). Current Inventory: %s";
        System.out.printf((log) + "%n", getTick(), farmer, amount, getCurrTick() - start, formatList(inventory));
    }

    public static void travel(String farmer, AnimalType place, int start) {
        String log = "%s %s | Travelled to %s (After %d ticks)";
        System.out.printf((log) + "%n", getTick(), farmer, formatField(place), getCurrTick() - start);
    }

    public static void animalDeposit(String farmer, int amount, AnimalType animalType, int start, int inField) {
        String log = "%s %s deposited %s (After %d ticks). Currently in field: %s";
        System.out.printf((log) + "%n", getTick(), farmer, formatCount(amount, animalType), getCurrTick() - start, formatCount(inField, animalType));
    }

    public static void buyerBought(String buyer, AnimalType animalType, int start) {
        String log = "%s %s bought 1 %s (After %d ticks)";
        System.out.printf((log) + "%n", getTick(), buyer, animalType, getCurrTick() - start);
    }

    public static void buyerWaiting(String buyer, AnimalType animal, int position) {
        String log = "%s %s wants %s. Queue position: %d";
        System.out.printf((log) + "%n", getTick(), buyer, animal, position);
    }

    private static String getTick() {
        return "\u001B[36mTick %d\033[0m\t|".formatted(getCurrTick());
    }

    private static String formatList(List<AnimalType> list) {
        var builder = new StringBuilder("[");
        for (AnimalType type : new LinkedHashSet<>(list)) {
            builder.append(formatCount(Collections.frequency(list, type), type));
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append("]");
        return builder.toString();
    }

    private static String formatCount(int count, AnimalType type) {
        return "\033[0;33m%dx\u001B[35m%s\033[0m".formatted(count, type);
    }

    private static String formatField(AnimalType type) {
        if (type == null) return "Enclosure";
        else return type + " Field";
    }
}