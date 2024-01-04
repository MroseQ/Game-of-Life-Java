package POLO2;

import java.util.List;
import java.util.Random;

import static POLO2.Settings.N;

public abstract class Animal extends Organism{

    public Animal()
    {
        super();
    }

    protected void action(){
        Random random = new Random();
        int randX, randY;
        do {
            if (this.getPosition().getX() == 1) randX = random.nextInt(2);
            else if (this.getPosition().getX() == N) randX = random.nextInt(2) - 1;
            else randX = random.nextInt(3) - 1;

            if (this.getPosition().getY() == 1) randY = random.nextInt(2);
		    else if (this.getPosition().getY() == N) randY = random.nextInt(2) - 1;
		    else randY = random.nextInt(3) - 1;

        } while (randX == 0 && randY == 0);
        this.setPreviousPosition(this.getPosition());
        this.addPosition(randX,randY);
        Organism other = this.getWorld().checkCollisionOnPosition(this.getPosition(),this);
        if (other != null) {
            this.collision(other);
        }
	    else {
            this.getWorld().pushEvent(new SystemEvent("Movement of object " + this.getID() +
                    " onto -> " + this.getPosition().print()));
        }
    }

    protected void collision(Organism other){
        Class<?> classOfOther = other.getClass();
        Class<?> classOfThis = this.getClass();
        if (classOfThis == classOfOther) {
            this.setPosition(this.getPreviousPosition());
            this.reproduce(this.getPosition(),other.getPosition());
            other.setAfterAction(true);
        }
        else {
            if (other instanceof Plant) {
                other.collision(this);
            }
            else {
                this.fight((Animal) other);
            }
        }
    }

    protected void death(Animal killer){
        this.getWorld().pushToRemove(this);
        killer.winBattle(this);
    }

    void winBattle(Animal victim){
        this.getWorld().pushEvent(new SystemEvent(this.getID() + " has won a battle with " + victim.getID()));
    }

    void fight(Animal defender){
        if (defender.getStrength() > this.getStrength()) {
            this.death(defender);
        }
	    else {
            defender.death(this);
        }
    }
    void reproduce(Position alfa, Position beta) {
        try {
            List<Position> possiblePlacesList = alfa.getNeighbours(beta);
            Position positionOfNewObject = null;
            for (Position p : possiblePlacesList) {
                if (!this.getWorld().isObjectOnPosition(p)){
                    positionOfNewObject = p;
                    break;
                }
            }
            if (positionOfNewObject != null) {
                this.getWorld().pushEvent(new SystemEvent("Species " + this.getClass().getSimpleName()
                        + " gave birth to a child on -> " + positionOfNewObject.print() ));
                //IMPLEMENTACJA DODAWANIA DZIECKA
            }
            else {
                this.getWorld().pushEvent(new SystemEvent("Species " + this.getClass().getSimpleName() + " was shy so there was no child."));
            }
        }
        catch (CustomException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
