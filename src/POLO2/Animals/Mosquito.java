package POLO2.Animals;

import POLO2.*;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Mosquito implements Animal{
    static Color color = new Color(150,0,0);

    @Override
    public void death(Organism killer){
        Random random = new Random();
        if(random.nextInt(2) == 0) Animal.super.death(killer);
        else{
            this.setPosition(this.getPreviousPosition());
            this.getWorld().pushEvent(new SystemEvent(this.getID() + " has survived a battle with " + killer.getID() + " and has landed on " + this.getPosition().print()));
        }
    }

    @Override
    public void action(){
        try {
            List<Position> nextToPlaces = this.getPosition().getNeighbours(this.getPosition());
            int counter = 0;
            for (Position p : nextToPlaces) {
                if (this.getWorld().isObjectOnPosition(p)
			        && this.getWorld().returnObjectFromPosition(p).getClass() == Mosquito.class)
                {
                    this.incStrength();
                    this.incInitiative();
                    counter++;
                }
            }
            if (counter != 0) {
                this.getWorld().pushEvent(new SystemEvent( this.getID() + " has boosted its stats for +" + counter));
            }
            Animal.super.action();
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Organism createChild(World world, Position position) {
        return new Organism.OrganismBuilder("Mosquito:",1,1,world,true).setPosition(position).build();
    }
}
