package POLO2.Animals;

import POLO2.*;

import java.awt.*;

public class Sheep extends Animal {
    public static final Color color = new Color(225,225,225);

    public Sheep(){
        super();
        setStrength(4);
        setInitiative(4);
        setPrefix("Sheep-");
    }
}
