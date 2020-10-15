package group10player;
import battlecode.common.*;

public class Miner extends Unit{
    boolean seenDesignSchool;
    boolean seenRefinery;


    final int maxVisionSquared = 34;

    int teamSoup;


    public Miner(RobotController rc) throws GameActionException {
        super(rc);
        seenDesignSchool = false;
        seenRefinery = false;
        teamSoup = 0;
    }

    @Override
    public void takeTurn() throws GameActionException {
        checkAndBuild_v2();
        //goMining();
    }

    public void checkAndBuild() throws GameActionException{
        teamSoup = rc.getTeamSoup();
        //check if we've seen a design school
        //if not, try to build one if we have enough soup
        //if we've seen a design school, check if we've seen a refinery
        //if not, try to build one if we have enough soup

        //temporarily using seenDesignSchool and seenRefinery as just "have I built this"
        if (!seenDesignSchool) {
            //try to build one
            if (teamSoup >= 150){
                for (Direction dir:directions){
                    if (tryBuild(RobotType.DESIGN_SCHOOL, dir)){
                        seenDesignSchool = true;
                        break;
                    }
                }
            }
        } else if (!seenRefinery) {
            //try to build one
            if (teamSoup >= 200){
                for (Direction dir:directions){
                    if (tryBuild(RobotType.REFINERY, dir)){
                        seenRefinery = true;
                        break;
                    }
                }
            }
        }
    }

    //this function uses scans to check if this miner has seen specific buildings, not just built them
    public void checkAndBuild_v2() throws GameActionException{
        teamSoup = rc.getTeamSoup();

        //Check array of nearby robots, and see if any allied robots are design schools or refineries
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots(maxVisionSquared, myTeam);
        for (RobotInfo nearbyRobot : nearbyRobots){
            if (nearbyRobot.type == RobotType.DESIGN_SCHOOL){
                seenDesignSchool = true;
            } else if (nearbyRobot.type == RobotType.REFINERY){
                seenRefinery = true;
            }
        }
        //check if we've seen a design school
        //if not, try to build one if we have enough soup
        //if we've seen a design school, check if we've seen a refinery
        //if not, try to build one if we have enough soup

        //temporarily using seenDesignSchool and seenRefinery as just "have I built this"
        if (!seenDesignSchool) {
            //try to build one
            if (teamSoup >= 150){
                for (Direction dir:directions){
                    if (tryBuild(RobotType.DESIGN_SCHOOL, dir)) {
                        break;
                        //don't need to update seenDesignSchool, because the miner should see it at the beginning of next turn
                    }
                }
            }
        } else if (!seenRefinery) {
            //try to build one
            if (teamSoup >= 200){
                for (Direction dir:directions){
                    if (tryBuild(RobotType.REFINERY, dir)) {
                        break;
                        //don't need to update seenRefinery, because the miner should see it at the beginning of next turn
                    }
                }
            }
        }
    }

    //if carrying soup, find somewhere to deposit it. Otherwise, go find and mine soup.
    public void goMining() throws GameActionException{
        //if carrying soup, deposit it
        MapLocation[] soupNearby = rc.senseNearbySoup();
        //walk in a random direction
        for (Direction dir:directions){
            if (rc.canMove(dir)){
                rc.move(dir);
                break;
            }
        }
    }

}
