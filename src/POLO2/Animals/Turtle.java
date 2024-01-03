package POLO2.Animals;

import POLO2.*;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Turtle implements Animal {
    static Color color = new Color(113,255,113);

    @Override
    public void death(Organism killer){
        if(killer.getStrength()<5){
            killer.setPosition(killer.getPreviousPosition());
            this.getWorld().pushEvent(new SystemEvent(this.getID() + " has defended himself from " + killer.getID()));
        }else{
            Animal.super.death(killer);
        }
    }

    @Override
    public void action(){
        Random random = new Random();
        if(random.nextInt(100) >= 75){
            Animal.super.action();
        }
    }

    @Override
    public Organism createChild(World world, Position position) {
        return new Organism.OrganismBuilder("Turtle:",2,1,world,true).setPosition(position).build();
    }
}
