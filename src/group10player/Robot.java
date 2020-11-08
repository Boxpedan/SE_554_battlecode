package group10player;

import battlecode.common.*;

import java.awt.*;

//Robot will receive "rc" as a variable in its constructor.
public class Robot {
    RobotController rc;

    Team myTeam;

    MapLocation HQLocation;
    MapLocation myLocation;
    Direction HQDirection;
    int initialHQElevation;
    int teamMessageCode; //our team's message code for identifying block chain communication.

    boolean HQLocationSent;
    int numRefineries;
    int numVaporators;
    MapLocation enemyHQLocation;

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
        if (myTeam == Team.A) { //use different team message codes so we can run our code against itself
            teamMessageCode = 159; //code is 159 if on team A
        }else{
            teamMessageCode = 265; //code is 265 if on team B
        }
        initialHQElevation = -1000;

        HQLocationSent = false;
        numRefineries = 0;
        numVaporators = 0;
        enemyHQLocation = null;

    }

    public void takeTurn() throws GameActionException{
        readBlockchainMessages(rc.getRoundNum() - 1); //read last round's messages, if any, and save info.
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

    //allows robot to update their location after being constructed
    public void setMyLocation(){
        myLocation = rc.getLocation();
    }

    public int [] buildBlockchainMessage(int int1, int int2, int int3, int int4, int int5, int int6, int int7){
        int [] message = new int[7]; // formatted {teamcode, messagecode, XPOS, YPOS, var, var, var}
        for (int x = 0; x < 7; x++){
            message[x] = 0;
        }
        message[0] = int1;
        message[1] = int2;
        message[2] = int3;
        message[3] = int4;
        message[4] = int5;
        message[5] = int6;
        message[6] = int7;
        return message;
    }

    public boolean trySendBlockchainMessage(int [] message, int cost) throws GameActionException{
        if (message.length != 7){
            System.out.println("Can only send messages with 7 ints!");
            return false;
        }
        if (cost < 1){
            System.out.println("Must bid at least 1 soup!");
            return false;
        }
        if (rc.canSubmitTransaction(message, cost)){
            rc.submitTransaction(message, cost);
            return true;
        }
        return false;
    }

    public void readBlockchainMessages(int roundNum){
        if (roundNum < 0){
            return;
        }

    }

    /* comment for helper functions.
    //allows robot to update HqLocation
    public void setHqLocation(MapLocation hq){
        HQLocation = hq;
    }
    //allows robot to update their team after being constructed
    public void setMyTeam(){
        myTeam = rc.getTeam();
    }
    //allows robot to update their HQ elevation used for testing
    public void setHQElevation(int num){
        initialHQElevation = 2;
    }

 */

}
