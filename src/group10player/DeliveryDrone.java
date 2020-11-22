package group10player;

import battlecode.common.*;
import scala.collection.Map;

import java.awt.Robot;

public class DeliveryDrone extends Unit {

    int target = -1;
    boolean holding_target = false;
    MapLocation water_loc = null;
    boolean know_water = false;
    int gameStage = 0;  //0: we don't know, request info
                        //1: early game, put landscapers onto wall
                        //2: landscapers have filled wall, surround in a ring
                        //3: ring is complete, group up and get ready to swarm enemy HQ

    public DeliveryDrone(RobotController rc) throws GameActionException {
        super(rc);
        gameStage = 0; //we don't know - request update from HQ
//        System.out.println("initialize delivery drone");
//        System.out.println("constructor myLocation: " + myLocation);
//        System.out.println("constructor myLocation: " + rc.getLocation());
    }



    //drone will move around randomly until it finds an enemy
    //when it finds an enemy it will attempt to go pick them up, preventing them from doing anything
    @Override
    public void takeTurn() throws GameActionException {
        super.takeTurn();

//        System.out.println("target: " + target);
//        System.out.println("holding_target: " + holding_target);

        if (gameStage == 0 && rc.getTeamSoup() >= 1){
            trySendBlockchainMessage(buildBlockchainMessage(8, 0, 0, 0, 0, 0, 0), 1);
        } else if (gameStage == 1){
            if (target == -1) {
                moveRandom();
                searchForLandscaper();
            } else if (!holding_target){
                grabLandscaper();
            } else { //drop on wall
                if (HQLocation == null){
                    tryFindHQLocation();
                }else{
                    dropOnWall();
                }
            }
        } else if (gameStage == 2){
            //drop any held things - enemies in water, allies on ground

////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //if no target move random and look for enemy
            if(target == -1) {
                moveRandom();
                searchForEnemy();

                if(!know_water) {
                    searchForWater();
                }
            }
            else if(!holding_target) //if have target move towards and try to pick up
            {
//            System.out.println("not holding");
                grabEnemy();
            }
            else
            {
//            System.out.println("else statement");
                if(know_water)
                {
//                System.out.println("in else know_water");
                    dropInWater();
                }
                else
                {
//                System.out.println("else else");
                    moveRandom();
//                System.out.println("after moveRandom");
                    searchForWater();
//                System.out.println("end of else else");
                }

            }

//        System.out.println("holding_target: " + holding_target);

/////////////////////////////////////////////////////////////////////////////////////////////////////

        } else if (gameStage == 3){

        }



    }

    public void moveRandom() throws GameActionException{
        Direction dir = randomDirection();
//        dir = Direction.SOUTH;
        tryMoveDirection(dir);
    }

    public void searchForWater() {
        MapLocation loc = null;
        MapLocation drone_loc = rc.getLocation();
        boolean sense_return = false;
        int test_x = 0;
        int test_y = 0;

        // to make things simpler, the drone will search only the 7 by 7 box around themselves
        //i represents the row/y, j represents the column/x
        //
        for(int i = 3; i >= -3; i--)
        {
            for(int j = -3; j <= 3; j++)
            {
                test_x = drone_loc.x + j;
                test_y = drone_loc.y + i;
                loc = new MapLocation(test_x, test_y);
                try {
                    sense_return = rc.senseFlooding(loc);
                } catch (GameActionException e)
                {
                    System.out.println("Trying to sense outside map");
                }

                if(sense_return) //if the tile is flooded
                {
                    water_loc = loc;
                    know_water = true;
//                    System.out.println("water found");

                }
            }
        }
    }

    public void dropInWater() throws GameActionException {
//        System.out.println("start dropInWater");
        myLocation = rc.getLocation();
        if (myLocation.distanceSquaredTo(water_loc) > 18) { //outside of 7x7 square around us
            searchForWater(); //first, check to see if we can see any water near us to start with.
        }
        MapLocation drone_loc = rc.getLocation();
        Direction water_dir = drone_loc.directionTo(water_loc);
        int distance = drone_loc.distanceSquaredTo(water_loc);

//        System.out.println("before if 0");
//        System.out.println("distance: " + distance);
        if(distance <= 0)
        {
            tryMoveDirection(randomDirection());
        }
//        System.out.println("before if 2");
        if(distance > 2)
        {

            tryMoveDirection(water_dir);
        }
//        System.out.println("before if 2 and 0");
        if(distance <= 2 && distance > 0) //not sure if they can drop on their own square
        {
//            System.out.println("before water_dire: " + water_dir);
            if(rc.canDropUnit(water_dir))
            {
                rc.dropUnit(water_dir);
                holding_target = false;
                target = -1;
            }
        }
//        System.out.println("end dropInWater");
    }

    public void dropOnWall() throws GameActionException {
        myLocation = rc.getLocation();
        if (HQLocation == null){
            tryFindHQLocation();
            return;
        }
        if (myLocation.distanceSquaredTo(HQLocation) > 8) { //get close enough to see entire wall
            tryMoveDirection(myLocation.directionTo(HQLocation));
        }

        MapLocation dropTarget = null;
        int minDistance = 100000;
        for (Direction dir:directions){
            if (rc.canSenseLocation(HQLocation.add(dir))) {
                RobotInfo temp = rc.senseRobotAtLocation(HQLocation.add(dir));
                if (temp == null) { //no robot there, try to drop
                    if (dropTarget == null || myLocation.distanceSquaredTo(HQLocation.add(dir)) > minDistance){
                        dropTarget = HQLocation.add(dir);
                        minDistance = myLocation.distanceSquaredTo(dropTarget);
                    }
                }
            }
        }
        if (dropTarget == null){
            //wall complete, wait for HQ to send gameStage update
            return;
        }

        int distance = myLocation.distanceSquaredTo(dropTarget);

        if(distance <= 0)
        {
            tryMoveDirection(randomDirection());
        }
        if(distance > 2)
        {

            tryMoveTowardsFavorRight(dropTarget);
        }
        if(distance <= 2 && distance > 0) //not sure if they can drop on their own square
        {
            if(rc.canDropUnit(myLocation.directionTo(dropTarget)))
            {
                rc.dropUnit(myLocation.directionTo(dropTarget));
                holding_target = false;
                target = -1;
            }
        }
    }

    public void searchForEnemy()
    {

        RobotInfo[] enemy_robots = rc.senseNearbyRobots(24, rc.getTeam().opponent());
        RobotInfo[] cows = rc.senseNearbyRobots(24, Team.NEUTRAL);
//
//        System.out.println("enemy_robots: " + enemy_robots);

        if(enemy_robots != null) {

            for (RobotInfo enemy_robot : enemy_robots) {

                if (enemy_robot.getType() == RobotType.LANDSCAPER || enemy_robot.getType() == RobotType.MINER) {
                    target = enemy_robot.getID();
                }
            }
        }

        if(cows != null) {

            for (RobotInfo cow : cows) {
                System.out.println("enemy_robot: " + cow.getType());
                if (cow.getType() == RobotType.COW) {
                    target = cow.getID();

                }
            }
        }

    }

    public void searchForLandscaper() throws GameActionException {
        RobotInfo[] allied_robots = rc.senseNearbyRobots(24, rc.getTeam());

        if (HQLocation == null){
            tryFindHQLocation();
            return;
        }

        if(allied_robots != null) {
            for (RobotInfo ally_robot : allied_robots) {
                if (ally_robot.getType() == RobotType.LANDSCAPER && !ally_robot.getLocation().isAdjacentTo(HQLocation)) {
                    target = ally_robot.getID();
                }
            }
        }
    }

    public void grabEnemy() throws GameActionException
    {
//        System.out.println("start grabEnemy");

        RobotInfo target_info = null;
        try{
            target_info = rc.senseRobot(target);
        }catch(GameActionException e)
        {
            target = -1;
            return;
        }
        int distance = rc.getLocation().distanceSquaredTo(target_info.getLocation());
        if(distance > 2)
        {
            Direction enemy_dir = rc.getLocation().directionTo(target_info.location);

            tryMoveDirection(enemy_dir);
        }

        if(rc.canPickUpUnit(target))
        {
            rc.pickUpUnit(target);
            holding_target = true;
        }

    }

    public void grabLandscaper() throws GameActionException
    {
        RobotInfo target_info = null;
        try{
            target_info = rc.senseRobot(target);
        }catch(GameActionException e)
        {
            target = -1;
            return;
        }
        if (HQLocation == null){
            tryFindHQLocation();
            return;
        }
        if (target_info.getLocation().isAdjacentTo(HQLocation)){
            target = -1;
            return;
        }
        int distance = rc.getLocation().distanceSquaredTo(target_info.getLocation());
        if(distance > 2)
        {
            Direction landscaper_dir = rc.getLocation().directionTo(target_info.location);

            tryMoveDirection(landscaper_dir);
        }

        if(rc.canPickUpUnit(target))
        {
            rc.pickUpUnit(target);
            holding_target = true;
        }

    }

    @Override
    public boolean tryMoveDirection(Direction dirTowards) throws GameActionException{
        if (dirTowards == null){
            return false;
        }
        for (int x = 0; x <= 8; x++) {
            if (rc.canMove(dirTowards)) {
                rc.move(dirTowards);
                return true;
            } else {
                for (int y = 0; y < x; y++) { //rotate direction back and forth
                    if (x % 2 == 1) {
                        dirTowards = dirTowards.rotateRight();
                    } else {
                        dirTowards = dirTowards.rotateLeft();
                    }
                }
            }
        }
        return false;
    }

//    /**
//     * Returns a random Direction.
//     *
//     * @return a random Direction
//     */
//    static Direction randomDirection() {
//        return directions[(int) (Math.random() * directions.length)];
//    }

    /*
    * Comment for unused helper functions.
    public int getTarget()
    {
        return target;
    }

    public void setTarget(int t)
    {
        target = t;
    }

    public Team getTeamOpponent()
    {
        return myTeam;
    }
    */

}
