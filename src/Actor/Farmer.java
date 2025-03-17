package Actor;

import Model.AnimalType;
import Model.Enclosure;
import Model.Field;
import Model.FieldRanking;
import Service.Logger;
import Service.TickEventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static Service.PropertyManager.getProp;
import static Service.TickLoopHandler.getCurrTick;

public class Farmer extends Thread {
    private static final int INVENTORY_CAP = getProp("farmer_inventory_size");
    private static final int MOVEMENT_DELAY = getProp("farmer_movement_delay");
    private static final int MOVEMENT_DELAY_PER_ANIMAL = getProp("farmer_movement_delay_per_animal");

    private final int id;
    private final TickEventHandler tickEvent;
    private final ConcurrentMap<AnimalType, Field> fields;
    private final List<AnimalType> inventory = new ArrayList<>();

    private String threadName;
    private AnimalType location;

    public Farmer(int id, TickEventHandler tickEvent, ConcurrentMap<AnimalType, Field> fields) {
        this.id = id;
        this.tickEvent = tickEvent;
        this.fields = fields;
    }

    @Override
    public void run() {
        threadName = Thread.currentThread().getName();
        while (true) {
            tickEvent.waitForEvent();
            attemptAnimalPickUp();
            attemptAnimalDeposit();
        }
    }

    private void attemptAnimalPickUp() {
        if (Enclosure.isEmpty() || inventory.size() >= INVENTORY_CAP) return; // Do not get animals if inventory is full

        goTo(null); // Null field = enclosure
        Enclosure.pickUpAnimals(this.toString(), tickEvent, inventory, INVENTORY_CAP);
    }

    private void attemptAnimalDeposit() {
        while (!inventory.isEmpty() && !areFieldsFull()) {
            var field = decideField();
            if (field == null) return; // If no field is suitable, try to pick up more animals

            // Tell farmers you plan to deposit animals in the field
            var interest = Collections.frequency(inventory, field.getAnimalType());
            field.showInterest(interest);

            goTo(field.getAnimalType());
            field.depositAnimals(this.toString(), tickEvent, inventory, interest);
        }
    }

    private Field decideField() {
        // Remove any fields that the farmer has no animals for
        // Also remove any fields which are full
        var values = fields.values().stream().filter(
                field -> inventory.contains(field.getAnimalType()) && !field.isFull()
        ).toList();

        // If all fields are full, choose no field
        if (values.isEmpty()) return null;

        // Rank how much fields need animals
        List<FieldRanking> rankings = new ArrayList<>();
        for (Field field : values) {
            int ranking = field.getBuyerQueueSize(); // Add +1 point per buyer waiting
            ranking += field.getAvailableSlotCount(); // Add +1 point per empty spot
            ranking -= field.getInterest(); // Remove -1 point per animal another farmer is putting there
            rankings.add(new FieldRanking(field, ranking));
        }

        // If multiple fields have the same rank, go with the one which we have the most animals for
        // In the case of a tie, go with any
        var max = Collections.max(rankings, Comparator.comparingInt(FieldRanking::ranking)).ranking();
        rankings = rankings.stream().filter(rank -> rank.ranking() == max).toList(); // Remove any which aren't max rank

        if (rankings.size() > 1) {
            rankings = rankings.stream()
                    // Change rank to be the amount of animals you have, prioritising what you have the most of
                    .map(rank -> new FieldRanking(rank.field(), Collections.frequency(inventory, rank.field())))
                    .toList();
            return Collections.max(rankings, Comparator.comparingInt(FieldRanking::ranking)).field();
        }

        return rankings.getFirst().field();
    }

    private boolean areFieldsFull() {
        return fields.values().stream().allMatch(Field::isFull);
    }

    private void goTo(AnimalType field) {
        if (location == field) return; // Do not penalise if already at location
        var start = getCurrTick();

        tickEvent.waitTicks(MOVEMENT_DELAY + MOVEMENT_DELAY_PER_ANIMAL * inventory.size());
        location = field;
        Logger.travel(this.toString(), location, start);
    }

    @Override
    public String toString() {
        return "Farmer %d (%s)".formatted(id, threadName);
    }
}
