package POLO2;

import java.util.ArrayList;
import java.util.List;

import static POLO2.Settings.N;
import static java.util.Collections.swap;

public class World {
    final int size;
    int turn;
    final Legend legend;
    List<SystemEvent> eventList = new ArrayList<>();
    List<Organism> worldOrganisms = new ArrayList<>();

    World(){
        this.size = N;
        this.turn = 0;
        this.legend = new Legend();
    }
    void performTurn(){
        turn++;
        sortOrganisms();
        for (Organism var : worldOrganisms) {
            if (var.isAfterTurn()) {
                this.pushEvent(new SystemEvent("Object " + var.getID() + " didn't have their action."));
                var.setAfterAction(false);
            } else if (var.getStun() != 0) {
                this.pushEvent(new SystemEvent("Object " + var.getID() + " is stunned."));
                var.setStun(var.getStun() - 1);
            } else {
                var.action();
            }
        }
        this.removeFromWorld();
    }
    void paintTheWorld(){

    }
    //void fetch();
    //void saveWorld();
    //World loadWorld();

    //void operator=(Swiat* other);

    public Organism checkCollisionOnPosition(Position p, Organism attacker){
        int count = 0;
        List<Organism> objectsOnPosition = new ArrayList<>();
        for (Organism var : worldOrganisms) {
            if (p == var.getPosition()) {
                count++;
                objectsOnPosition.add(var);
            }
        }
        if (count <= 1) return null;
        else {
            for (Organism var : objectsOnPosition) {
                if (attacker != var) return var;
            }
        }
        return null;
    }
    public boolean isObjectOnPosition(Position p){
        for (Organism var : worldOrganisms) {
            if (p == var.getPosition()) {
                return true;
            }
        }
        return false;
    }

    public boolean isObjectOnPosition(int x, int y){
        for (Organism var : worldOrganisms) {
            if (var.getPosition() != null && x == var.getPosition().getX() && y == var.getPosition().getY()) {
                return true;
            }
        }
        return false;
    }
    public Organism returnObjectFromPosition(Position p){
        for (Organism var : worldOrganisms) {
            if (p == var.getPosition()) {
                return var;
            }
        }
        return null;
    }
    public Organism returnObjectFromPosition(int x, int y){
        for (Organism var : worldOrganisms) {
            if (x == var.getPosition().getX() && y == var.getPosition().getY()) {
                return var;
            }
        }
        return null;
    }

    public int getTurn() { return turn; }

    public void pushEvent(SystemEvent e){
        boolean doNotPush = false;
        for (SystemEvent event : eventList) {
            if (e.getEvent().equals(event.getEvent())) {
                doNotPush = true;
                break;
            }
        }
        if (!doNotPush) {
            this.eventList.add(e);
        }
    }
    public void pushToRemove(Organism o){
        this.pushEvent(new SystemEvent("Object " + o.getID() + " dies. [*]"));
        o.setPosition(-1,-1);
        o.setIsDead(true);
        o.setAfterAction(true);
    }
    void pushToWorld(Organism o){
        this.worldOrganisms.add(o);
    }

    void removeFromWorld(){
        /*
        Iterator<Organism> iterator = worldOrganisms.iterator();
        while (iterator.hasNext()) {
            Organism var = iterator.next();
            if (var.getIsDead()) {
                iterator.remove();
            }
        }*/
        worldOrganisms.removeIf(Organism::getIsDead);

    }
    void sortOrganisms(){
        for (int i = 0; i < worldOrganisms.size() - 1; i++) {
            for (int j = 0; j < worldOrganisms.size() - i - 1; j++) {
                if (worldOrganisms.get(j).getInitiative() < worldOrganisms.get(j + 1).getInitiative() ||
                        (worldOrganisms.get(j).getInitiative() == worldOrganisms.get(j + 1).getInitiative() &&
                        worldOrganisms.get(j).getAliveSince() > worldOrganisms.get(j + 1).getAliveSince())) {
                    swap(worldOrganisms,j,j + 1);
                }
            }
        }
    }
}
