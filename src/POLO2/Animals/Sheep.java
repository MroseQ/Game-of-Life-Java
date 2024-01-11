package POLO2.Animals;

import POLO2.*;

import java.awt.*;

public class Sheep extends Animal {
    public static final Color color = new Color(200,130,200);

    public Sheep(){
        super();
        setStrength(4);
        setInitiative(4);
        setPrefix("Sheep-");
        this.setColor(color);
    }
}
