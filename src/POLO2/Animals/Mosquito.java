package POLO2.Animals;

import POLO2.*;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Mosquito extends Animal {
    public static final Color color = new Color(150, 0, 0);

    public Mosquito() {
        super();
        this.setStrength(1);
        this.setInitiative(1);
        this.setPrefix("Mosquito-");
        this.setColor(color);
    }

    @Override
    protected void death(Animal killer) {
        Random random = new Random();
        if (random.nextInt(2) == 0) super.death(killer);
        else {
            if (!this.getPreviousPosition().equals(new Position(-1,-1))) {
                this.setPosition(this.getPreviousPosition());
                this.getWorld().pushEvent(new SystemEvent(this.getID() + " has survived a battle with " + killer.getID() + " and has landed on " + this.getPosition().print()));
            } else {
                try {
                    boolean hasNewPosition = false;
                    for (Position p : this.getPosition().getNeighbours(this.getPosition(), this.getWorld().getSize())) {
                        if (!this.getWorld().isObjectOnPosition(p)) {
                            this.setPosition(p);
                            this.getWorld().pushEvent(new SystemEvent(this.getID() + " has survived a battle with " + killer.getID() + " and has landed on " + this.getPosition().print()));
                            hasNewPosition = true;
                            break;
                        }
                    }
                    if(!hasNewPosition){
                        super.death(killer);
                    }
                } catch (CustomException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    protected void action() {
        try {
            List<Position> nextToPlaces = this.getPosition().getNeighbours(this.getPosition(), this.getWorld().getSize());
            int counter = 0;
            for (Position p : nextToPlaces) {
                if (this.getWorld().isObjectOnPosition(p)
                        && this.getWorld().returnObjectFromPosition(p).getClass() == Mosquito.class) {
                    this.incStrength();
                    this.incInitiative();
                    counter++;
                }
            }
            if (counter != 0) {
                this.getWorld().pushEvent(new SystemEvent(this.getID() + " has boosted its stats for +" + counter));
            }
            super.action();
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }
}
