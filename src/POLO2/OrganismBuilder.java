package POLO2;

import POLO2.Animals.*;
import POLO2.Plants.*;

import java.util.Objects;
import java.util.Random;

import static POLO2.Settings.N;

public class OrganismBuilder {
    boolean isAfterAction = false;
    String id = null;
    final World world;

    Position position = null;

    public OrganismBuilder(World world) {
        this.world = world;
    }

    public OrganismBuilder setAfterAction(boolean bool) {
        this.isAfterAction = bool;
        return this;
    }

    public OrganismBuilder setID(String id) {
        this.id = id;
        return this;
    }

    public OrganismBuilder setPosition(Position p) {
        this.position = p;
        return this;
    }

    public OrganismBuilder setPosition(int x, int y) {
        this.position = new Position(x, y);
        return this;
    }

    public Organism build(String name) {
        Organism newObject = null;
        if (name.equals("Wolf")) {
            newObject = new Wolf();
        } else if (name.equals("Sheep")) {
            newObject = new Sheep();
        } else if (name.equals("Mosquito")) {
            newObject = new Mosquito();
        } else if (name.equals("Turtle")) {
            newObject = new Turtle();
        } else if (name.equals("Zebra")) {
            newObject = new Zebra();
        } else if (name.equals("Hedgehog")) {
            newObject = new Hedgehog();
        } else if (name.equals("Grass")) {
            newObject = new Grass();
        } else if (name.equals("Guarana")) {
            newObject = new Guarana();
        } else if (name.equals("Wolfberries")) {
            newObject = new Wolfberries();
        }
        if (newObject != null) {
            fillOutWithData(newObject);
        }
        return newObject;
    }

    private void fillOutWithData(Organism object) {
        object.setID(Objects.requireNonNullElseGet(this.id, () -> object.getPrefix() + Organism.addNextID()));
        object.setStun(0);
        object.setWorld(this.world);
        object.getWorld().pushToWorld(object);
        object.setPreviousPosition(new Position(-1, -1));
        object.setAfterAction(this.isAfterAction);
        object.setAliveSince(this.world.getTurn());
        object.setIsDead(false);
        if (this.position == null) {
            int newX, newY;
            do {
                Random random = new Random();
                newX = random.nextInt(N) + 1;
                newY = random.nextInt(N) + 1;
            } while (this.world.isObjectOnPosition(newX, newY));
            object.setPosition(new Position(newX, newY));
        } else {
            object.setPosition(this.position);
        }
        world.pushEvent(new SystemEvent("Spawned - " + object.getClass().getSimpleName()));
    }
}
