package POLO2.Animals;

import POLO2.*;

import java.awt.*;

public class Wolf extends Animal {
    public static final Color color = new Color(65,28,0);

    public Wolf(){
        this.setStrength(9);
        this.setInitiative(5);
        this.setPrefix("Wolf-");
    }
}
