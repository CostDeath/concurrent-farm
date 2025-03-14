package Actor;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class BuyerWrapper extends Thread {
    List<Buyer> buyerQueue = new Stack<>();
    Random spawnRate = new Random();

    void spawnBuyer() {

    }

    @Override
    public void run() {
        while (true) {

            // if buyer not in field
            // stack.pop().start()
        }
    }
}
