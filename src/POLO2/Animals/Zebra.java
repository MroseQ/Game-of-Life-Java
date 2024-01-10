package POLO2.Animals;

import POLO2.*;
import POLO2.Animals.Wolf;

import java.awt.*;
import java.util.List;

public class Zebra extends Animal{
    public static final Color color = new Color(150,150,150);

    public Zebra(){
        super();
        this.setPrefix("Zebra-");
        this.setStrength(4);
        this.setInitiative(6);
        this.setColor(color);
    }

    @Override
    protected void death(Animal killer){
        if(this.getWorld().getTurn() % 2 == 0){
            this.getWorld().pushEvent(new SystemEvent( this.getID() + " has ran away from " + killer.getID() + " to -> " + killer.getPreviousPosition().print() ));
            this.setPosition(killer.getPreviousPosition());
            if(killer.getInitiative() > this.getInitiative()){
                this.setAfterAction(true);
            }
        }else{
            super.death(killer);
        }
    }

    @Override
    protected void action(){
        try {
            List<Position> nextToPositions = this.getPosition().getNeighbours(this.getPosition());
            Position wolfPosition = null;
            for (Position p : nextToPositions) {
                if (this.getWorld().isObjectOnPosition(p)
			        && this.getWorld().returnObjectFromPosition(p).getClass() == Wolf.class)
                {
                    wolfPosition = this.getWorld().returnObjectFromPosition(p).getPosition();
                    break;
                }
            }
            if (wolfPosition == null) {
                super.action();
            }
            else {
                int distX = this.getPosition().getX() - wolfPosition.getX();
                int distY = this.getPosition().getY() - wolfPosition.getY();
                this.setPreviousPosition(this.getPosition());
                this.addPosition(distX, distY);
                Organism other = this.getWorld().checkCollisionOnPosition(this.getPosition(),this);
                if (other != null) {
                    this.collision(other);
                }
		        else {
                    this.getWorld().pushEvent(new SystemEvent("Movement of object " + this.getID() +
                            " onto -> " + this.getPosition().print()));
                }
            }
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }
}
