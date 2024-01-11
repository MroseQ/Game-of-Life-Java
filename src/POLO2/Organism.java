package POLO2;

import java.awt.*;

public abstract class Organism {
    boolean isDead, isAfterAction;
    static int nextID = 0;
    int stun, strength, initiative, initialStrength, initialInitiative, aliveSince;
    String id,prefix;
    World world;
    Position position, previousPosition;
    Color color;

    abstract void action();
    abstract void collision(Organism other);

    public void createChild(World world,Position position){
        new OrganismBuilder(world).setPosition(position).setAfterAction(true).build(this.getClass().getSimpleName());
    }

    public Color paint(){
        return this.color;
    }

    public void setColor(Color color) { this.color = color;}

    public boolean isAfterTurn() { return this.isAfterAction; }

    public void setAfterAction(boolean b) { this.isAfterAction = b; }

    public int getStun() { return this.stun; }
    public void setStun(int value) { this.stun = value; }

    public int getAliveSince() { return aliveSince; }
    public void setAliveSince(int turn) { this.aliveSince = turn; }

    static int getNextID() { return Organism.nextID++; }
    static void setNextID(int value) { Organism.nextID = value; }

    public String getID() { return id; }
    public void setID(String message) { this.id = message; }

    public int getStrength() { return strength; }
    public void setStrength(int value) { this.strength = value; }
    public void incStrength() { this.strength++; }

    public boolean getIsDead() { return isDead; }
    public void setIsDead(boolean isDead) { this.isDead = isDead; }

    public Position getPosition() { return this.position; }
    public void setPosition(Position p) { this.position = p; }
    public void setPosition(int x, int y) { this.position = new Position(x,y); }
    public void addPosition(int x, int y) { this.position = new Position(this.position.getX()+x,this.position.getY()+y); }

    public int getInitiative() { return this.initiative; }
    public void setInitiative(int value) {this.initiative = value;}
    public void incInitiative() { this.initiative++; }

    public void setInitialInitiative(int value) {
        this.initialInitiative = value;
    }
    public void setInitialStrength(int value){
        this.initialStrength = value;
    }
    public Position getPreviousPosition() { return this.previousPosition; }
    public void setPreviousPosition(Position p) { this.previousPosition = p; }

    public void setPrefix(String string) { this.prefix = string;}
    public World getWorld() { return world; }
    public void setWorld(World world) { this.world = world; }

}
