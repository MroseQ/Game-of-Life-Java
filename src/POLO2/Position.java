package POLO2;

import java.util.ArrayList;
import java.util.List;

import static POLO2.Settings.N;
import static java.lang.Math.pow;

public class Position {
    private int x, y;

    public Position(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Position getPosition() {
        return this;
    }

    public void setPosition(Position other){
        this.x = other.x;
        this.y = other.y;
    }

    public String print(){
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean equals(Position other){
        return (x == other.x && y == other.y);
    }

    // Returns list of neighbouring Positions with another Position object.
    public List<Position> getNeighbours(Position other) throws CustomException {
        List<Position> list;

        if (this.x == other.x && this.y == other.y) {
            list = neighboursOfSinglePosition();
        }
        else {
            if (this.y == other.y) {
                list = neighboursOfSameY(other);
            }
            else if (this.x == other.x) {
                list = neighboursOfSameX(other);
            }
            else {
                double distanceX = pow((this.x - other.x), 2), distanceY = pow((this.y - other.y), 2);
                if (distanceX == 1 && distanceY == 1) {
                    list = new ArrayList<>();
                    list.add(0, new Position(this.x,other.y));
                    list.add(1, new Position(other.x,this.y));
                }
                else {
                    throw new CustomException("Positions: " + this.print() + " and " + other.print() +" aren't neighbours!");
                }
            }
        }
        return list;
    }


    private List<Position> neighboursOfSameY(Position other){
        List<Position> list = new ArrayList<>();
        int index = 0;
        if (this.y + 1 <= N) {
            list.add(index, new Position(this.x,this.y+1));
            index++;
            list.add(index, new Position(other.x,this.y+1));
            index++;
        }
        if (this.y - 1 > 0) {
            list.add(index, new Position(this.x,this.y-1));
            index++;
            list.add(index, new Position(other.x,this.y-1));
        }
        return list;
    }

    private List<Position> neighboursOfSameX(Position other){
        List<Position> list = new ArrayList<>();
        int index = 0;
        if (this.x + 1 <= N) {
            list.add(index, new Position(this.x+1,this.y));
            index++;
            list.add(index, new Position(this.x+1,other.y));
            index++;
        }
        if (this.x - 1 > 0) {
            list.add(index, new Position(this.x-1,this.y));
            index++;
            list.add(index, new Position(this.x-1,other.y));
        }
        return list;
    }

    private List<Position> neighboursOfSinglePosition() {
        List<Position> list = new ArrayList<>();
        int x = this.x;
        int y = this.y;
        int index = 0;
        if (x > 1) {
            list.add(index, new Position(x-1,y));
            index++;
            if (y > 1) {
                list.add(index, new Position(x-1,y-1));
                index++;
            }
            if (y < N) {
                list.add(index, new Position(x-1,y+1));
                index++;
            }
        }
        if (x < N) {
            list.add(index, new Position(x+1,y));
            index++;
            if (y > 1) {
                list.add(index, new Position(x+1,y-1));
                index++;
            }
            if (y < N) {
                list.add(index, new Position(x+1,y+1));
                index++;
            }
        }
        if (y > 1) {
            list.add(index, new Position(x,y-1));
            index++;
        }
        if (y < N) {
            list.add(index, new Position(x,y+1));
        }
        return list;
    }
}
