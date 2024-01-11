package POLO2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static POLO2.Settings.*;
import static java.lang.Math.min;
import static java.util.Collections.swap;

public class World {
    final int size;
    int turn;
    final Legend legend;
    List<SystemEvent> eventList = new ArrayList<>();
    List<Organism> worldOrganisms = new ArrayList<>();

    private JComponent contentComponent,eventComponent,legendComponent;

    World(){
        this.size = N;
        this.turn = 0;
        this.legend = new Legend();
    }
    void performTurns(){
        turn++;
        sortOrganisms();
        for (int i=0;i<worldOrganisms.size();i++) {
            Organism var = worldOrganisms.get(i);
                System.out.println(var.getID() + "  " + var.getPosition().print() + "  " + var.getIsDead());
            if(!var.getIsDead()){
                Position position = var.getPosition();
                JComponent component = (JComponent) contentComponent.getComponent((N + 1) * position.getY() + position.getX());
                component.setBackground(new Color(238, 238, 238));
                if (var.isAfterTurn()) {
                    this.pushEvent(new SystemEvent(var.getID() + " didn't have their action."));
                    var.setAfterAction(false);
                } else if (var.getStun() != 0) {
                    this.pushEvent(new SystemEvent(var.getID() + " is stunned."));
                    var.setStun(var.getStun() - 1);
                } else {
                    var.action();
                }
            }
        }
        this.removeFromWorld();
        for (Organism worldOrganism : worldOrganisms) {
            worldOrganism.setAfterAction(false);
        }
        paintTheWorld();
    }
    void paintTheWorld(){
        JLabel turnLabel = (JLabel) contentComponent.getComponent(0);
        turnLabel.setText("T:"+turn);
        if(turnLabel.getText().length() > 3){
            turnLabel.setFont(turnLabel.getFont().deriveFont((float)(contentWidth/(2*(N+1))-(turnLabel.getText().length()*4))));
        }
        for(Organism var : worldOrganisms) {
            System.out.println("PAINTING: " + var.getID() + "  " + var.getPosition().print());
            if(!var.getIsDead()){
                Position position = var.getPosition();
                JComponent component = (JComponent) contentComponent.getComponent((N+1) *position.getY() + position.getX());
                component.setBackground(var.paint());
            }
        }
        printEvents();
        this.legend.resize(worldOrganisms,legendComponent);
    }

    void printEvents(){
        eventComponent.removeAll();
        eventComponent.updateUI();
        eventComponent.setLayout(new GridLayout(eventList.size(),1));
        for(int i=1;i<=eventList.size();i++){
            JLabel label = new JLabel(i+ " => "  + eventList.get(i-1).getEvent());
            label.setFont(label.getFont().deriveFont((float)eventHeight/(10+eventList.size())));
            eventComponent.add(label);
        }
        eventList.clear();
    }

    void saveWorld(){

    }

    void setContentComponent(JComponent contentComponent){
        this.contentComponent = contentComponent;
    }

    void setEventComponent(JComponent eventComponent){
        this.eventComponent = eventComponent;
    }

    void setLegendComponent(JComponent legendComponent){
        this.legendComponent = legendComponent;
    }
    World loadWorld(){
        return this;
    }

    public Organism checkCollisionOnPosition(Position p, Organism attacker){
        Organism objectOnPosition = null;
        for (Organism var : worldOrganisms) {
            if (p.equals(var.getPosition()) && !var.equals(attacker)) {
                objectOnPosition = var;
                break;
            }
        }
        return objectOnPosition;
    }
    public boolean isObjectOnPosition(Position p){
        for (Organism var : worldOrganisms) {
            if (p.equals(var.getPosition())) {
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
            if (p.equals(var.getPosition())) {
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

    public void repaintCell(Position position){
        JComponent component = (JComponent) contentComponent.getComponent((N + 1) * position.getY() + position.getX());
        component.setBackground(new Color(238, 238, 238));
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
