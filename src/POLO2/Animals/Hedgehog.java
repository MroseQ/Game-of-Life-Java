package POLO2.Animals;

import POLO2.*;

import java.awt.*;

public class Hedgehog extends Animal {
    public static final Color color = new Color(212, 134, 0);

    public Hedgehog() {
        super();
        this.setStrength(2);
        this.setInitiative(3);
        this.setPrefix("Hedgehog-");
        this.setColor(color);
    }

    @Override
    protected void death(Animal killer) {
        this.getWorld().pushEvent(new SystemEvent(this.getID() + " has stunned " + killer.getID() + " for 2 turns."));
        killer.setStun(2);
        super.death(killer);
    }
}
