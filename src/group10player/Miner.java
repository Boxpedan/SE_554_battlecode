package group10player;
import battlecode.common.*;
import java.util.*;

public class Miner extends Unit{
    boolean seenDesignSchool;
    boolean seenRefinery;
    boolean seenFulfillmentCenter;


    final int maxVisionSquared = 34;

    int teamSoup;

    Set<MapLocation> depositLocations;

    final int maxRefineries = 1;
    final int maxVaporators = 1;
    final int maxDesignSchools = 1;
    final int maxFulfillmentCenters = 1;


    public Miner(RobotController rc) throws GameActionException {
        super(rc);
        seenDesignSchool = false;
        seenRefinery = false;
        seenFulfillmentCenter = false;
        teamSoup = 0;
        depositLocations = new HashSet<MapLocation>();
        if (tryFindHQLocation()){
            depositLocations.add(HQLocation);
        }
    }

    @Override
    public void takeTurn() throws GameActionException {
        super.takeTurn();
        checkAndBuild();
        goMining();
    }

    //this function uses scans to check if this miner has seen specific buildings, not just built them
    public void checkAndBuild() throws GameActionException{
        myLocation = rc.getLocation();
        teamSoup = rc.getTeamSoup();

        //Check array of nearby robots, and see if any allied robots are design schools or refineries
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots(maxVisionSquared, myTeam);
        //System.out.println("am i here" + nearbyRobots.length);
        if (nearbyRobots != null && nearbyRobots.length > 0) {
            for (RobotInfo nearbyRobot : nearbyRobots) {
                if (nearbyRobot.type == RobotType.DESIGN_SCHOOL) {
                    seenDesignSchool = true;
                } else if (nearbyRobot.type == RobotType.REFINERY) {
                    seenRefinery = true;
                    depositLocations.add(nearbyRobot.getLocation()); //sets don't duplicate when adding
                    if (HQLocation != null) {
                        depositLocations.remove(HQLocation); //we want to deposit at refineries whenever we can
                    }
                } else if (nearbyRobot.type == RobotType.FULFILLMENT_CENTER) {
                    seenFulfillmentCenter = true;
                }
            }
        }
        //check if we've seen a refinery
        //if not, try to build one if we have enough soup
        //if we've seen a refinery, check if we've seen a design school
        //if not, try to build one if we have enough soup
        //if we've seen a design school, check if we've seen a fulfillment center
        //if not, try to build one if we have enough soup

        if (!seenRefinery && numRefineries < maxRefineries) {
            //try to build one
            if (teamSoup >= 201){
                for (Direction dir:directions){
                    if (!myLocation.add(dir).isAdjacentTo(HQLocation) && !(myLocation.add(dir).distanceSquaredTo(HQLocation) <= 8) && tryBuild(RobotType.REFINERY, dir)) {
                        if (rc.getTeamSoup() >= 1){
                            trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 2, myLocation.add(dir).x, myLocation.add(dir).y, 0, 0, 0), 1);
                        }
                        break;
                        //don't need to update seenRefinery, because the miner should see it at the beginning of next turn
                    }
                }
            }
        } else if (!seenDesignSchool && numDesignSchools < maxDesignSchools) {
            //try to build one
            if (teamSoup >= 205){
                for (Direction dir:directions){
                    if (!myLocation.add(dir).isAdjacentTo(HQLocation) && !(myLocation.add(dir).distanceSquaredTo(HQLocation) <= 8) && tryBuild(RobotType.DESIGN_SCHOOL, dir)) {
                        if (rc.getTeamSoup() >= 1){
                            trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 4, myLocation.add(dir).x, myLocation.add(dir).y, 0, 0, 0), 1);
                        }
                        break;
                        //don't need to update seenDesignSchool, because the miner should see it at the beginning of next turn
                    }
                }
            }
        } else if (!seenFulfillmentCenter && numFulfillmentCenters < maxFulfillmentCenters){
            if (teamSoup >= 205){
                for (Direction dir:directions){
                    if (!myLocation.add(dir).isAdjacentTo(HQLocation) && !(myLocation.add(dir).distanceSquaredTo(HQLocation) <= 8) && tryBuild(RobotType.FULFILLMENT_CENTER, dir)) {
                        if (rc.getTeamSoup() >= 1){
                            trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 5, myLocation.add(dir).x, myLocation.add(dir).y, 0, 0, 0), 1);
                        }
                        break;
                        //don't need to update seenFulfillmentCenter, because the miner should see it at the beginning of next turn
                    }
                }
            }
        }
    }

    //if carrying soup, find somewhere to deposit it. Otherwise, go find and mine soup.
    public void goMining() throws GameActionException{
        if (HQLocation == null){
            if (tryFindHQLocation()){
                depositLocations.add(HQLocation);
            }
            return;
        }
        myLocation = rc.getLocation();
        //miners mine 7 soup per turn, and can hold up to 100
        //if carrying soup, deposit it
        if (rc.getSoupCarrying() >= 94){
            MapLocation targetLocation = null;
            int minDistance = 10000000;
            if (depositLocations == null || depositLocations.size() < 1){
                System.out.println("I don't have any deposit locations!");
                return;
            }
            for (MapLocation testLocation : depositLocations){
                if (myLocation.distanceSquaredTo(testLocation) < minDistance){
                    targetLocation = testLocation;
                    minDistance = myLocation.distanceSquaredTo(testLocation);
                }
            }
            if (targetLocation == null){
                System.out.println("I didn't find any deposit locations!");
                return;
            }
            Direction targetDirection = myLocation.directionTo(targetLocation);
            //move towards location, or deposit if already adjacent
            if (myLocation.isAdjacentTo(targetLocation)) { // adjacent to target location
                if (rc.canDepositSoup(targetDirection)) {
                    rc.depositSoup(targetDirection, rc.getSoupCarrying());
                }
            } else {  // try to move in the direction of the target, but if you can't, just rotate movement until you can move
                tryMoveDirection(targetDirection);
            }
        } else { //not carrying soup; find soup, move towards it, and mine it (in that order)
            MapLocation[] soupNearby = rc.senseNearbySoup();
            if (soupNearby == null || soupNearby.length < 1){//no nearby soup found, wander away from HQ
                tryMoveDirection(myLocation.directionTo(HQLocation).opposite());
                return;
            }
            int closestDistance = 1000000;
            MapLocation nearestSoup = null;
            int x = 0;
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
