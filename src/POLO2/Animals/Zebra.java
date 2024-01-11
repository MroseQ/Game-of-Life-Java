package POLO2.Animals;

import POLO2.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static POLO2.Settings.N;

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
                this.setPreviousPosition(this.getPosition());
                this.setPosition(escapingTheWolf(wolfPosition));
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
    Position escapingTheWolf(Position wolfPosition){
        int zebraX = this.getPosition().getX();
        int zebraY = this.getPosition().getY();
        if(zebraX != 1 && zebraX != N && zebraY != 1 && zebraY != N){
            int distX = zebraX - wolfPosition.getX();
            int distY = zebraY - wolfPosition.getY();
            return new Position(zebraX+distX,zebraY+distY);
        }
        List<Position> possiblePositions = new ArrayList<>();
        try {
            boolean isXBorder = (zebraX == 1 || zebraX == N);
            boolean isYBorder = (zebraY == 1 || zebraY == N);
            if (isXBorder && isYBorder) {
                possiblePositions = this.getPosition().getNeighbours(wolfPosition);
            }else{
                if(isXBorder){
                    if(zebraY == wolfPosition.getY()){
                        possiblePositions = this.getPosition().getNeighbours(wolfPosition);
                    }
                    else if(zebraY > wolfPosition.getY()){
                        possiblePositions.add(new Position(zebraX,zebraY+1));
                        if(zebraX == 1){
                            possiblePositions.add(new Position(zebraX+1,zebraY+1));
                        }else{
                            possiblePositions.add(new Position(zebraX-1,zebraY+1));
                        }
                    }else{
                        possiblePositions.add(new Position(zebraX,zebraY-1));
                        if(zebraX == 1){
                            possiblePositions.add(new Position(zebraX+1,zebraY-1));
                        }else{
                            possiblePositions.add(new Position(zebraX-1,zebraY-1));
                        }
                    }
                }else{
                    if(zebraX == wolfPosition.getX()){
                        possiblePositions = this.getPosition().getNeighbours(wolfPosition);
                    }
                    else if(zebraX > wolfPosition.getX()){
                        possiblePositions.add(new Position(zebraX+1,zebraY));
                        if(zebraY == 1){
                            possiblePositions.add(new Position(zebraX+1,zebraY+1));
                        }else{
                            possiblePositions.add(new Position(zebraX+1,zebraY-1));
                        }
                    }else{
                        possiblePositions.add(new Position(zebraX-1,zebraY));
                        if(zebraY == 1){
                            possiblePositions.add(new Position(zebraX-1,zebraY+1));
                        }else{
                            possiblePositions.add(new Position(zebraX-1,zebraY-1));
                        }
                    }
                }
            }
        }catch(CustomException e){
            System.out.println(e.getMessage());
        }
        Random random = new Random();
        return possiblePositions.get(random.nextInt(possiblePositions.size()));
    }
}
