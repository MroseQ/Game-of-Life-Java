package POLO2.Plants;

import POLO2.Organism;
import POLO2.Plant;

import java.awt.*;

public class Wolfberries extends Plant {
    public static final Color color = new Color(51, 0, 102);

    public Wolfberries() {
        super();
        this.setPrefix("Wolfberries-");
        this.setColor(color);
    }

    @Override
    protected void collision(Organism taker) {
        this.getWorld().pushToRemove(taker);
        super.collision(taker);
    }
}
