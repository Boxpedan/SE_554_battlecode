package group10player;

import battlecode.common.*;

//Robot will receive "rc" as a variable in its constructor.
public class Robot {
    RobotController rc;
    public Robot(RobotController rcTemp) throws GameActionException{
        rc = rcTemp;
    }
}
