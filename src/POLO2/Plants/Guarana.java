package POLO2.Plants;

import POLO2.*;

import java.awt.*;

public class Guarana extends Plant {
    public static final Color color = new Color(255,153,51);

    public Guarana(){
        super();
        this.setPrefix("Guarana-");
    }

    @Override
    protected void collision(Organism taker){
        taker.setStrength(taker.getStrength()+3);
        super.collision(taker);
    }
}
