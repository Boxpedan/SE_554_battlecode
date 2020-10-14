package group10player;

import battlecode.common.*;

//Robot will receive "rc" as a variable in its constructor.
public class Robot {

    static Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST
    };

    RobotController rc;
    public Robot(RobotController rcTemp) throws GameActionException{
        rc = rcTemp;
    }

    public void takeTurn() throws GameActionException{

    }
}
