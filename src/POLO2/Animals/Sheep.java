package POLO2.Animals;

import POLO2.Animal;
import POLO2.Organism;
import POLO2.Position;
import POLO2.World;

import java.awt.*;

public class Sheep implements Animal {
    static Color color = new Color(225,225,225);

    @Override
    public Organism createChild(World world, Position position) {
        return new OrganismBuilder("Sheep:",4,4,world,true).setPosition(position).build();
    }
}
