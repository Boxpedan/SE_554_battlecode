package group10player;
import battlecode.common.*;

import java.util.Map;

/* Blockchain Message Guide:

7 ints
First int: Team code (159 or 265, depending on which team we're on)
Second int: Type of message
Third int: Generally x-coordinate
Fourth int: Generally y-coordinate
Fifth, Sixth, Seventh ints: context-dependant

Message codes (second int):
0 - HQ Location
1 - Enemy HQ Location
2 - Refinery built
3 - Vaporator built
4 - Design school
5 - Fulfillment center
6 - Netgun
7 -
8 - Request gameStage update from HQ
9 - gameStage update response

10 - New unit update request
2xx - HQ response to new unit update request (position 0 is team, 1 is xpos of enemy HQ + 200, 2 is ypos of enemy HQ)



*/

public class HQ extends Building{
    int numMiners = 0;
    int limitMiners = 4;
    int MAX_LIMIT_MINER = 10;
    boolean hasSentLocation;

    public HQ(RobotController rc) throws GameActionException {
        super(rc);
        hasSentLocation = false;
        gameStage = 1; //early game
    }
    
    @Override
    public void takeTurn() throws GameActionException{
        super.takeTurn();
        System.out.println("Round: "+ rc.getRoundNum());

        SendHQlocBlockchain();

        Shooter();

        UpdateGameStage();

        UpdateBlockchain();



        if(numMiners < limitMiners){
            for (Direction dir: Robot.directions){
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;
                    if (numMiners >= limitMiners){
                        break;
                    }
                }
            }
        }
        if(numMiners >= limitMiners&& numMiners <= MAX_LIMIT_MINER && rc.getTeamSoup() >= 510){
            for (Direction dir: Robot.directions){
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;
                    break;
                }
            }
        }
        //System.out.println("Total amount of soup is: "+rc.getTeamSoup());

    }

    //Function to send the HQloc
    public void SendHQlocBlockchain() throws GameActionException {
        if (!HQLocationSent){                   // we're willing to pay 29, in order to make sure
            if (trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 0, rc.getLocation().x, rc.getLocation().y, rc.senseElevation(rc.getLocation()), 0, 0), 29)){
                HQLocationSent = true;          // that no strategies for flooding the blockchain on turn 1
            }                                   // make it so our message isn't sent.
        }
    }

    public void Shooter() throws GameActionException {
        //shoot nearby delivery drones
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        if (!(nearbyRobots == null || nearbyRobots.length < 1)) {
            for (RobotInfo nearbyRobot : nearbyRobots) {
                if (nearbyRobot.type == RobotType.DELIVERY_DRONE && nearbyRobot.getTeam() == rc.getTeam().opponent()) {
                    if (rc.canShootUnit(nearbyRobot.getID())){
                        rc.shootUnit(nearbyRobot.getID());
                    }
                }
            }
        }
    }

    public void UpdateBlockchain() throws GameActionException { //every 50 rounds, resend enemy HQ location to blockchain to make sure all units are up to date
        if (HQUpdateRequested){
            if (enemyHQLocation != null){
                if (rc.getTeamSoup() >= 1){
                    trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, enemyHQLocation.x+200, enemyHQLocation.y, numNetgun, numVaporators, numDesignSchools, numFulfillmentCenters), 1);
                }
            }else {
                if (rc.getTeamSoup() >= 1) {
                    trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 199, numNetgun, numRefineries, numVaporators, numDesignSchools, numFulfillmentCenters), 1);
                }
            }
            HQUpdateRequested = false;
        }

        if (gameStageUpdateRequested && rc.getTeamSoup() >= 1){
            System.out.println("Trying to update blockchain");
            if (trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 9, gameStage, 0, 0, 0, 0), 1)) {
                gameStageUpdateRequested = false;
            }
        }

        if (enemyHQLocation == null){
            return;
        }
        if (rc.getRoundNum() % 50 == 0){
            if (rc.getTeamSoup() >= 5){
                trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 1, enemyHQLocation.x, enemyHQLocation.y, 0, 0, 0), 5);
            }else{
                trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 1, enemyHQLocation.x, enemyHQLocation.y, 0, 0, 0), rc.getTeamSoup());
            }
        }
    }

    public void UpdateGameStage() throws GameActionException{
        myLocation = rc.getLocation();
        if (gameStage == 1){ //currently early game, check if surrounded by landscapers
            for (Direction dir:directions){
                RobotInfo temp = rc.senseRobotAtLocation(myLocation.add(dir));
                if (temp == null){
                    return;
                }
                if (temp.getTeam() != rc.getTeam() || temp.getType() != RobotType.LANDSCAPER){
                    return;
                }
            }
            gameStage = 2;
            gameStageUpdateRequested = true;
        } else if (gameStage == 2){ //currently late game, check if ring of drones is complete
            MapLocation toCheck;
            MapLocation toCheck2;
            for (int increment = 0; increment < 5; increment++){
                toCheck = new MapLocation(myLocation.x-2+increment, myLocation.y+2);
                if (rc.canSenseLocation(toCheck)){
                    RobotInfo temp = rc.senseRobotAtLocation(toCheck);
                    if (temp == null){
                        return;
                    }
                    if (temp.getTeam() != rc.getTeam() || temp.getType() != RobotType.DELIVERY_DRONE){
                        return;
                    }
                }
                toCheck2 = new MapLocation(myLocation.x-2+increment, myLocation.y-2);
                if (rc.canSenseLocation(toCheck2)){
                    RobotInfo temp = rc.senseRobotAtLocation(toCheck2);
                    if (temp == null){
                        return;
                    }
                    if (temp.getTeam() != rc.getTeam() || temp.getType() != RobotType.DELIVERY_DRONE){
                        return;
                    }
                }
            }
            for (int increment = 0; increment < 3; increment++){
                toCheck = new MapLocation(myLocation.x-2, myLocation.y-1+increment);
                if (rc.canSenseLocation(toCheck)){
                    RobotInfo temp = rc.senseRobotAtLocation(toCheck);
                    if (temp == null){
                        return;
                    }
                    if (temp.getTeam() != rc.getTeam() || temp.getType() != RobotType.DELIVERY_DRONE){
                        return;
                    }
                }
                toCheck2 = new MapLocation(myLocation.x+2, myLocation.y-1+increment);
                if (rc.canSenseLocation(toCheck2)){
                    RobotInfo temp = rc.senseRobotAtLocation(toCheck2);
                    if (temp == null){
                        return;
                    }
                    if (temp.getTeam() != rc.getTeam() || temp.getType() != RobotType.DELIVERY_DRONE){
                        return;
                    }
                }
            }
            //if we haven't returned by now, then we haven't found any breaks in the ring, so:
            gameStage = 3;
            gameStageUpdateRequested = true;
        }
    }
}




