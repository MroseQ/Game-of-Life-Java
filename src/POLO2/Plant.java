package POLO2;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

import static POLO2.Settings.N;
import static POLO2.Settings.spawnRate;

public abstract class Plant extends Organism{

    public Plant(){
        super();
        this.setStrength(0);
        this.setInitiative(0);
    }

    void action(){
        Random random = new Random();
        if (random.nextInt(100) < spawnRate) {
            this.reproduce(this.getPosition());
        }
    }

    protected void collision(Organism taker){
        this.getWorld().pushEvent(new SystemEvent(taker.getID() + " has eaten " + this.getID()));
        this.getWorld().repaintCell(this.getPosition());
        this.getWorld().pushToRemove(this);
    }


    void reproduce(Position position){
        try {
            List<Position> possiblePositions = position.getNeighbours(position);
            Random random = new Random();
            int startIndex = random.nextInt(possiblePositions.size());
            int index = startIndex;
            Position newPosition = null;
            do {
                if (index+1 > possiblePositions.size()-1) {
                    index = 0;
                }
                else {
                    index++;
                }
                if (!this.getWorld().isObjectOnPosition(possiblePositions.get(index))) {
                    newPosition = possiblePositions.get(index);
                    break;
                }
            } while (index!=startIndex);

            if (newPosition != null) {
                this.createChild(this.getWorld(), newPosition);
                this.getWorld().pushEvent(new SystemEvent("Plant " + this.getID() +
                        " has spread onto -> " + newPosition.print()));
            }
            else {
                this.getWorld().pushEvent(new SystemEvent("Species " + this.getClass().getSimpleName() + "' didn't have a place to reproduce."));
            }
        } catch (CustomException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
