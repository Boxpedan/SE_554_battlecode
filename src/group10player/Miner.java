package group10player;
import battlecode.common.*;

public class Miner extends Unit{
    boolean seenDesignSchool;
    boolean seenRefinery;
    boolean seenFulfillmentCenter;


    final int maxVisionSquared = 34;

    int teamSoup;


    public Miner(RobotController rc) throws GameActionException {
        super(rc);
        seenDesignSchool = false;
        seenRefinery = false;
        seenFulfillmentCenter = false;
        teamSoup = 0;
        tryFindHQLocation();
    }

    @Override
    public void takeTurn() throws GameActionException {
        checkAndBuild();
        goMining();
    }

    //this function uses scans to check if this miner has seen specific buildings, not just built them
    public void checkAndBuild() throws GameActionException{
        myLocation = rc.getLocation();
        teamSoup = rc.getTeamSoup();

        //Check array of nearby robots, and see if any allied robots are design schools or refineries
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots(maxVisionSquared, myTeam);
        if (nearbyRobots != null) {
            for (RobotInfo nearbyRobot : nearbyRobots) {
                if (nearbyRobot.type == RobotType.DESIGN_SCHOOL) {
                    seenDesignSchool = true;
                } else if (nearbyRobot.type == RobotType.REFINERY) {
                    seenRefinery = true;
                } else if (nearbyRobot.type == RobotType.FULFILLMENT_CENTER) {
                    seenFulfillmentCenter = true;
                }
            }
        }
        //check if we've seen a design school
        //if not, try to build one if we have enough soup
        //if we've seen a design school, check if we've seen a refinery
        //if not, try to build one if we have enough soup
        //if we've seen a refinery, check if we've seen a fulfillment center
        //if not, try to build one if we have enough soup

        //temporarily using seenDesignSchool and seenRefinery as just "have I built this"
        if (!seenDesignSchool) {
            //try to build one
            if (teamSoup >= 150){
                for (Direction dir:directions){
                    if (!myLocation.add(dir).isAdjacentTo(HQLocation) && tryBuild(RobotType.DESIGN_SCHOOL, dir)) {
                        break;
                        //don't need to update seenDesignSchool, because the miner should see it at the beginning of next turn
                    }
                }
            }
        } else if (!seenRefinery) {
            //try to build one
            if (teamSoup >= 200){
                for (Direction dir:directions){
                    if (!myLocation.add(dir).isAdjacentTo(HQLocation) && tryBuild(RobotType.REFINERY, dir)) {
                        break;
                        //don't need to update seenRefinery, because the miner should see it at the beginning of next turn
                    }
                }
            }
        } else if (!seenFulfillmentCenter){
            if (teamSoup >= 150){
                for (Direction dir:directions){
                    if (!myLocation.add(dir).isAdjacentTo(HQLocation) && tryBuild(RobotType.FULFILLMENT_CENTER, dir)) {
                        break;
                        //don't need to update seenFulfillmentCenter, because the miner should see it at the beginning of next turn
                    }
                }
            }
        }
    }

    //if carrying soup, find somewhere to deposit it. Otherwise, go find and mine soup.
    public void goMining() throws GameActionException{
        myLocation = rc.getLocation();
        //miners mine 7 soup per turn, and can hold up to 100
        //if carrying soup, deposit it
        if (rc.getSoupCarrying() >= 94){
            HQDirection = myLocation.directionTo(HQLocation);
            //move towards HQ, or deposit if already adjacent
            if (myLocation.isAdjacentTo(HQLocation)) { // adjacent to HQ
                if (rc.canDepositSoup(HQDirection)) {
                    rc.depositSoup(HQDirection, rc.getSoupCarrying());
                }
            } else {  // try to move in the direction of the HQ, but if you can't, just rotate movement until you can move
                tryMoveDirection(HQDirection);
            }
        } else { //not carrying soup; find soup, move towards it, and mine it (in that order)
            MapLocation[] soupNearby = rc.senseNearbySoup();
            if (soupNearby == null){//no nearby soup found, wander away from HQ
                tryMoveDirection(myLocation.directionTo(HQLocation).opposite());
                return;
            }
            int closestDistance = 1000000;
            MapLocation nearestSoup = null;
            for (MapLocation soupSpot : soupNearby) {
                if (myLocation.distanceSquaredTo(soupSpot) < closestDistance) {
                    nearestSoup = soupSpot;
                    closestDistance = myLocation.distanceSquaredTo(soupSpot);
                }
            }
            if (nearestSoup == null){ //some error in finding nearest soup
                System.out.println("MINER: ERROR IN PROCESSING NEAREST SOUP!");
                return;
            }
            Direction dirToSoup = myLocation.directionTo(nearestSoup);
            //move towards soup, or mine it if already adjacent
            if (myLocation.distanceSquaredTo(nearestSoup) <= 2) { // adjacent to soup
                if (rc.canMineSoup(dirToSoup)) {
                    rc.mineSoup(dirToSoup);
                }
            } else {  // try to move in the direction of the soup, but if you can't, just rotate movement until you can move
                tryMoveDirection(dirToSoup);
            }
        }
    }
}
