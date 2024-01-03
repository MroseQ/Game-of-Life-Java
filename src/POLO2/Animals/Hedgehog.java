package POLO2.Animals;

import POLO2.*;

import java.awt.*;

public class Hedgehog implements Animal {
    static Color color = new Color(212,134,0);

    @Override
    public void death(Organism killer){
        this.getWorld().pushEvent(new SystemEvent(this.getID() + " has stunned " + killer.getID() + " for 2 turns."));
        killer.setStun(2);
        Animal.super.death(killer);
    }

    @Override
    public Organism createChild(World world, Position position) {
        return new Organism.OrganismBuilder("Hedgehog:",2,3,world,true).setPosition(position).build();
    }
}
