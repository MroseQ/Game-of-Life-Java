package POLO2.Plants;

import POLO2.*;

import java.awt.*;

public class Grass extends Plant {
    public static final Color color = new Color(102,255,102);

    public Grass(){
        super();
        this.setPrefix("Grass-");
        this.setColor(color);
    }
}
