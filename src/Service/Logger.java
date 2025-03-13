package Service;

public abstract class Logger {
    public static void animalArrival(int tick, int amt, String list) {
        String log = "Tick %d\t| %d animals arrived in the enclosure. Current enclosure: %s";
        System.out.printf((log) + "%n", tick, amt, list);
    }

    public static void travel(int tick, int id, String place, int ticks) {
        String log = "Tick %d\t| Farmer %d\t| Travelled to %s (after %d ticks)";
        System.out.printf((log) + "%n", tick, id, place, ticks);
    }
}
