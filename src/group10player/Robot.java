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

    static int teamMessageCode = 159; //our team's message code for identifying block chain communication.

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
        Transaction [] tBlock = rc.getBlock(1);
        for (int x = 0; x < tBlock.length; x++){
            int[] tempMessage = tBlock[x].getMessage();
            if (tempMessage[0] == teamMessageCode){ //run through each message, check initial int for our team's code
                if (tempMessage[1] == 000){ //found our HQ location message
                    HQLocation = new MapLocation(tempMessage[2], tempMessage[3]);
                    HQDirection = myLocation.directionTo(HQLocation);
                    return true;
                }
            }
        }

        System.out.println("I couldn't find our HQ using the blockchain!");

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
