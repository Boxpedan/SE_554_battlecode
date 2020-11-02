package group10player;

import battlecode.common.*;

//Robot will receive "rc" as a variable in its constructor.
public class Robot {
    RobotController rc;

    Team myTeam;

    MapLocation HQLocation;
    MapLocation myLocation;
    Direction HQDirection;
    int initialHQElevation;

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

    int teamMessageCode; //our team's message code for identifying block chain communication.

    static Direction randomDirection() {
        return directions[(int) (Math.random() * directions.length)];
    }

    public Robot(RobotController rcTemp) throws GameActionException{
        System.out.println("initialize robot");
        rc = rcTemp;
        myTeam = rc.getTeam();
        System.out.println("init myTeam: " + myTeam);
        myLocation = rc.getLocation();
        System.out.println("init myLocation: " + myLocation);
        if (myTeam == Team.A) { //use different team message codes so we can run our code against itself
            teamMessageCode = 159; //code is 159 if on team A
        }else{
            teamMessageCode = 265; //code is 265 if on team B
        }
        initialHQElevation = -1000;

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
        if (tBlock != null) {
            for (int x = 0; x < tBlock.length; x++) {
                int[] tempMessage = tBlock[x].getMessage();
                if (tempMessage[0] == teamMessageCode) { //run through each message, check initial int for our team's code
                    if (tempMessage[1] == 000) { //found our HQ location message
                        HQLocation = new MapLocation(tempMessage[2], tempMessage[3]);
                        HQDirection = myLocation.directionTo(HQLocation);
                        initialHQElevation = tempMessage[4];
                        return true;
                    }
                }
            }
        }

        System.out.println("I couldn't find our HQ using the blockchain!");

        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        if (nearbyRobots == null || nearbyRobots.length < 1){
            return false; //no robots nearby to find HQ from
        }
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
