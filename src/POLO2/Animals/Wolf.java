package POLO2.Animals;

import POLO2.Animal;
import POLO2.Organism;
import POLO2.Position;
import POLO2.World;

import java.awt.*;

public class Wolf implements Animal {

    static Color color = new Color(65,28,0);

    @Override
    public Organism createChild(World world, Position position) {
        return new OrganismBuilder("Wolf:",9,5,world,true).setPosition(position).build();
    }
}
