package POLO2;

import java.awt.*;

public abstract class Organism {
    public static int nextID = 0;
    private boolean isDead, isAfterAction;
    private int stun, strength, initiative, aliveSince;
    private String id, prefix;
    private World world;
    private Position position, previousPosition;
    private Color color;

    protected static int addNextID() {
        return Organism.nextID++;
    }

    protected static int getNextID() {
        return Organism.nextID;
    }

    protected static void setNextID(int value) {
        Organism.nextID = value;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Color paint() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isAfterTurn() {
        return this.isAfterAction;
    }

    public void setAfterAction(boolean b) {
        this.isAfterAction = b;
    }

    public int getStun() {
        return this.stun;
    }

    public void setStun(int value) {
        this.stun = value;
    }

    public int getAliveSince() {
        return aliveSince;
    }

    public void setAliveSince(int turn) {
        this.aliveSince = turn;
    }

    public String getID() {
        return id;
    }

    public void setID(String message) {
        this.id = message;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int value) {
        this.strength = value;
    }

    public void incStrength() {
        this.strength++;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position p) {
        this.position = p;
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    public void addPosition(int x, int y) {
        this.position = new Position(this.position.x() + x, this.position.y() + y);
    }

    public int getInitiative() {
        return this.initiative;
    }

    public void setInitiative(int value) {
        this.initiative = value;
    }

    public void incInitiative() {
        this.initiative++;
    }

    public Position getPreviousPosition() {
        return this.previousPosition;
    }

    public void setPreviousPosition(Position p) {
        this.previousPosition = p;
    }

    public void setPrefix(String string) {
        this.prefix = string;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    protected abstract void action();

    protected abstract void collision(Organism other);

    protected void createChild(World world, Position position) {
        new OrganismBuilder(world).setPosition(position).setAfterAction(true).build(this.getClass().getSimpleName());
    }

}
