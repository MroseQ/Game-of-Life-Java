package POLO2;

import java.awt.*;
import java.util.Random;

import static POLO2.Settings.N;

public interface Organism {
    boolean isDead, isAfterAction;
    int nextID = 0;
    int stun, strength, initiative, initialStrength, initialInitiative, aliveSince;
    String id,prefix;
    World world;
    Position position, previousPosition;
    Color color;

    Organism(OrganismBuilder builder)
    {
        if (id == null) {
            this.id = prefix + getNextID();
        }
        else {
            this.id = builder.id;
        }
        this.stun = 0;
        this.strength = builder.strength;
        this.initialStrength = builder.strength;
        this.initiative = builder.initiative;
        this.initialInitiative = builder.initiative;
        this.world = builder.world;
        this.world.pushToWorld(this);
        this.previousPosition = new Position(-1,-1);
        this.isAfterAction = builder.isAfterAction;
        this.aliveSince = this.world.turn;
        this.isDead = false;

        if (builder.position == null) {
            int newX, newY;
            do {
                Random random = new Random();
                newX = random.nextInt(N) + 1;
                newY = random.nextInt(N) + 1;
            } while (this.world.isObjectOnPosition(newX,newY));
            this.position = new Position(newX,newY);
        }
        else {
            this.position = builder.position;
        }
    }

    public static class OrganismBuilder{
        boolean isAfterAction;
        int strength, initiative;
        String id,prefix;
        World world;

        Position position;
        OrganismBuilder(String prefix,int strength,int initiative, World world, boolean isAfterAction){
            this.prefix = prefix;
            this.strength = strength;
            this.initiative = initiative;
            this.world = world;
            this.isAfterAction = isAfterAction;
        }

        public OrganismBuilder setID(String id){
            this.id = id;
            return this;
        }

        public OrganismBuilder setPosition(Position p){
            this.position = p;
            return this;
        }

        public Organism build(){
            return new Organism(this);
        }
    }

    void action();
    void collision(Organism other);

    void death(Organism killer);
    void winBattle(Organism victim);
    Color paint();

    default boolean isAfterTurn() { return this.isAfterAction; }
    default void setAfterAction(boolean b) { this.isAfterAction = b; }

    default int getStun() { return this.stun; }
    default void setStun(int value) { this.stun = value; }

    default int getAliveSince() { return aliveSince; }
    default void setAliveSince(int turn) { this.aliveSince = turn; }

    static int getNextID() { return nextID++; }
    static void setNextID(int value) { nextID = value; }

    default String getID() { return id; }
    default void setID(String message) { this.id = message; }

    default int getStrength() { return strength; }
    default void setStrength(int value) { this.strength = value; }
    default void incStrength() { this.strength++; }

    default boolean getIsDead() { return isDead; }
    default void setIsDead(boolean isDead) { this.isDead = isDead; }

    default Position getPosition() { return this.position; }
    default void setPosition(Position p) { this.position.setPosition(p); }
    default void setPosition(int x, int y) { this.position.setPosition(new Position(x,y)); }
    default void addPosition(int x, int y) { this.position.setPosition(new Position(this.position.getX()+x,this.position.getY()+y)); }

    default int getInitiative() { return this.initiative; }
    default void incInitiative() { this.initiative++; }

    default Position getPreviousPosition() { return this.previousPosition; }
    default void setPreviousPosition(Position p) { this.previousPosition = p; }

    default World getWorld() { return world; }
    default void setWorld(World world) { this.world = world; }

}
