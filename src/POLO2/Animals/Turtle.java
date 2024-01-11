package POLO2.Animals;

import POLO2.*;

import java.awt.*;
import java.util.Random;

public class Turtle extends Animal {
    public static final Color color = new Color(100,200,0);

    public Turtle(){
        super();
        this.setPrefix("Turtle-");
        this.setStrength(2);
        this.setInitiative(1);
        this.setColor(color);
    }

    @Override
    protected void death(Animal killer){
        if(killer.getStrength()<5){
            killer.setPosition(killer.getPreviousPosition());
            this.getWorld().pushEvent(new SystemEvent(this.getID() + " has defended himself from " + killer.getID()));
        }else{
            super.death(killer);
        }
    }

    @Override
    public void action(){
        Random random = new Random();
        if(random.nextInt(100) >= 75){
            super.action();
        }
    }
}
