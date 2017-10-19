package Homework_lesson_17_GoodCode.Source;

public class Tractor {

    Position position = new Position (0, 0);
    Position field = new Position (5, 5);
    Orientation orientation = Orientation.NORTH;

    public void move ( String command ) {
        if (command == "F") {
            moveForwards ();
        } else if (command == "T") {
            turnClockwise ();
        }
    }

    public void moveForwards () {
        switch (orientation){
            case NORTH:
                position.Y += 1;
                break;
            case EAST:
                position.X += 1;
                break;
            case SOUTH:
                position.Y -= 1;
                break;
            case WEST:
                position.X -= 1;
                break;
        }
        if (position.X > field.X || position.Y > field.Y) {
            throw new TractorInDitchException ();
        }
    }

    public void turnClockwise () {
        switch (orientation) {
            case WEST:
                orientation = Orientation.NORTH;
                break;
            case EAST:
                orientation = Orientation.SOUTH;
                break;
            case SOUTH:
                orientation = Orientation.WEST;
                break;
            case NORTH:
                orientation = Orientation.EAST;
                break;
        }
    }

    public int getPositionX () {
        return position.X;
    }

    public int getPositionY () {
        return position.Y;
    }

    public Orientation getOrientation () {
        return orientation;
    }

    private class Position {
        public Position ( int x, int y ) {
            X = x;
            Y = y;
        }
        int X;
        int Y;
    }

}
