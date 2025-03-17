package Model;

import java.util.List;

import static Service.PropertyManager.getProp;
import static Service.TickLoopHandler.getCurrTick;

public class Field {
    public static final int MAX_OCCUPANCY = getProp("max_field_occupancy");
    public static final int DROP_OFF_DELAY = getProp("field_drop_off_delay");

    private final AnimalType animalType;
    private int currAmount = 0;

    public Field(AnimalType animalType) {
        this.animalType = animalType;
    }

}
