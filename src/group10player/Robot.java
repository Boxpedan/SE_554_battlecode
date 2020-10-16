package group10player;

import battlecode.common.*;

//Robot will receive "rc" as a variable in its constructor.
public class Robot {
    RobotController rc;

    Team myTeam;

    MapLocation HQLocation;
    MapLocation myLocation;
    Direction HQDirection;

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

    static Direction randomDirection() {
        return directions[(int) (Math.random() * directions.length)];
    }

    public Robot(RobotController rcTemp) throws GameActionException{
        rc = rcTemp;
        myTeam = rc.getTeam();
        myLocation = rc.getLocation();


    }

    public void takeTurn() throws GameActionException{

    }

    public boolean tryBuild(RobotType type, Direction dir) throws GameActionException{
        if (rc.canBuildRobot(type, dir) && rc.isReady()){
            rc.buildRobot(type, dir);
            return true;
        }else{
            return false;
        }
    }

    public boolean tryFindHQLocation() throws GameActionException{
        //find HQ and save its location (eventually will use the blockchain instead)
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        for (RobotInfo nearbyRobot : nearbyRobots){
            if (nearbyRobot.type == RobotType.HQ && nearbyRobot.getTeam() == rc.getTeam()){
                HQLocation = nearbyRobot.getLocation();
                HQDirection = myLocation.directionTo(HQLocation);
                return true;
            }
        }
        return false;
    }


}
