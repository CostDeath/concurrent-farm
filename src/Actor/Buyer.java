package Actor;

public class Buyer extends Thread {
    private final int id;
    private String threadName;

    @Override
    public void run() {
        // Functionality Here
    }

    @Override
    public String toString() {
        return String.format("Buyer %d (%s)", this.id, this.threadName);
    }
}
