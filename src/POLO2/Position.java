package POLO2;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public record Position(int x, int y) {
    public static Position stringDecoder(String s) {
        String[] values = s.split(",");
        int x = Integer.parseInt(values[0].substring(1));
        int y = Integer.parseInt(values[1].substring(0, values[1].length() - 1));
        return new Position(x, y);
    }

    public boolean equals(Position other) {
        return (x == other.x && y == other.y);
    }

    public String print() {
        return "(" + this.x + "," + this.y + ")";
    }

    // Returns list of neighbouring Positions with another Position object.
    public List<Position> getNeighbours(Position other, int size) throws CustomException {
        List<Position> list;

        if (this.x == other.x && this.y == other.y) {
            list = neighboursOfSinglePosition(size);
        } else {
            if (this.y == other.y) {
                list = neighboursOfSameY(other, size);
            } else if (this.x == other.x) {
                list = neighboursOfSameX(other, size);
            } else {
                double distanceX = pow((this.x - other.x), 2), distanceY = pow((this.y - other.y), 2);
                if (distanceX == 1 && distanceY == 1) {
                    list = new ArrayList<>();
                    list.add(0, new Position(this.x, other.y));
                    list.add(1, new Position(other.x, this.y));
                } else {
                    throw new CustomException("Positions: " + this.print() + " and " + other.print() + " aren't neighbours!");
                }
            }
        }
        return list;
    }

    private List<Position> neighboursOfSameY(Position other, int size) {
        List<Position> list = new ArrayList<>();
        int index = 0;
        if (this.y + 1 <= size) {
            list.add(index, new Position(this.x, this.y + 1));
            index++;
            list.add(index, new Position(other.x, this.y + 1));
            index++;
        }
        if (this.y - 1 > 0) {
            list.add(index, new Position(this.x, this.y - 1));
            index++;
            list.add(index, new Position(other.x, this.y - 1));
        }
        return list;
    }

    private List<Position> neighboursOfSameX(Position other, int size) {
        List<Position> list = new ArrayList<>();
        int index = 0;
        if (this.x + 1 <= size) {
            list.add(index, new Position(this.x + 1, this.y));
            index++;
            list.add(index, new Position(this.x + 1, other.y));
            index++;
        }
        if (this.x - 1 > 0) {
            list.add(index, new Position(this.x - 1, this.y));
            index++;
            list.add(index, new Position(this.x - 1, other.y));
        }
        return list;
    }

    private List<Position> neighboursOfSinglePosition(int size) {
        List<Position> list = new ArrayList<>();
        int x = this.x;
        int y = this.y;
        int index = 0;
        if (x > 1) {
            list.add(index, new Position(x - 1, y));
            index++;
            if (y > 1) {
                list.add(index, new Position(x - 1, y - 1));
                index++;
            }
            if (y <
                    size) {
                list.add(index, new Position(x - 1, y + 1));
                index++;
            }
        }
        if (x < size) {
            list.add(index, new Position(x + 1, y));
            index++;
            if (y > 1) {
                list.add(index, new Position(x + 1, y - 1));
                index++;
            }
            if (y < size) {
                list.add(index, new Position(x + 1, y + 1));
                index++;
            }
        }
        if (y > 1) {
            list.add(index, new Position(x, y - 1));
            index++;
        }
        if (y < size) {
            list.add(index, new Position(x, y + 1));
        }
        return list;
    }
}
