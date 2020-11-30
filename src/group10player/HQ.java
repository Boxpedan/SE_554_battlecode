package group10player;
import battlecode.common.*;

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
    }
    
    @Override
    public void takeTurn() throws GameActionException{
        super.takeTurn();
        System.out.println("Round: "+ rc.getRoundNum());
        
        SendHQlocBlockchain();

        Shooter();

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
}




