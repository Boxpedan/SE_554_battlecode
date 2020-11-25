package group10player;

import battlecode.common.*;
import scala.collection.Map;

import java.awt.Robot;
import java.util.ArrayList;

public class DeliveryDrone extends Unit {

    int target = -1;
    boolean holding_target = false;
    MapLocation water_loc = null;
    boolean know_water = false;
    int ring_offset_x = -2;
    int ring_offset_y = 2;
    //    MapLocation current_ring_destination;
    ArrayList<MapLocation> ring_positions = new ArrayList<>();
    int current_ring_pos = 0;
    boolean init_ring_pos = false;

    boolean enemy_priority_override = false;
    //int gameStage;    //0: we don't know, request info
                        //1: early game, put landscapers onto wall
                        //2: landscapers have filled wall, surround in a ring
                        //3: ring is complete, group up and get ready to swarm enemy HQ

    public DeliveryDrone(RobotController rc) throws GameActionException {
        super(rc);
    }



    //drone will move around randomly until it finds an enemy
    //when it finds an enemy it will attempt to go pick them up, preventing them from doing anything
    @Override
    public void takeTurn() throws GameActionException {
        super.takeTurn();

//        System.out.println("target: " + target);
//        System.out.println("holding_target: " + holding_target);

        if(HQLocation == null)
        {
            tryFindHQLocation();
        }
//        System.out.println("init_ring_pos: " + init_ring_pos);
//        System.out.println("HQLocation: " + HQLocation);
//        System.out.println("ring_positions size: " + ring_positions.size());
        if(!init_ring_pos && HQLocation != null)
        {
//            System.out.println("test 1");
//            ring_positions.add(new MapLocation(HQLocation.x, ring_offset_y));
//            System.out.println("test 2");
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x, HQLocation.y + ring_offset_y));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 1, HQLocation.y + ring_offset_y));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 2, HQLocation.y + ring_offset_y));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 3, HQLocation.y + ring_offset_y));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 4, HQLocation.y + ring_offset_y));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 4, HQLocation.y + ring_offset_y - 1));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 4, HQLocation.y + ring_offset_y - 2));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 4, HQLocation.y + ring_offset_y - 3));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 4, HQLocation.y + ring_offset_y - 4));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 3, HQLocation.y + ring_offset_y - 4));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 2, HQLocation.y + ring_offset_y - 4));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x + 1, HQLocation.y + ring_offset_y - 4));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x, HQLocation.y + ring_offset_y - 4));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x, HQLocation.y + ring_offset_y - 3));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x, HQLocation.y + ring_offset_y - 2));
            ring_positions.add(new MapLocation(HQLocation.x + ring_offset_x, HQLocation.y + ring_offset_y - 1));
            init_ring_pos = true;

        }


        if (gameStage == 0 && rc.getTeamSoup() >= 1){
            trySendBlockchainMessage(buildBlockchainMessage(teamMessageCode, 8, 0, 0, 0, 0, 0), 1);
        } else if (gameStage == 1){
            if (target == -1) {
                if (HQLocation == null){
                    tryFindHQLocation();
                    System.out.println("Drone searching for HQ!");
                } else {
                    if (rc.getLocation().distanceSquaredTo(HQLocation) > 18){
                        tryMoveDirection(rc.getLocation().directionTo(HQLocation));
                    } else {
                        moveRandom();
                    }
                }
                searchForLandscaper();
                if(!know_water) {
                    searchForWater();
                }
            } else if (!holding_target){
                if (enemy_priority_override){ //pick up enemy
                    grabEnemy();
                } else {
                    grabLandscaper();
                }
            } else { //drop on wall
                if (enemy_priority_override){ //drop in water
                    if(know_water) {
                        dropInWater();
                    } else {
                        moveRandom();
                        searchForWater();
                    }
                } else {
                    if (HQLocation == null) {
                        tryFindHQLocation();
                    } else {
                        dropOnWall();
                    }
                }
            }
        } else if (gameStage == 2){
            //drop any held things - enemies in water, allies on ground
            surroundHQ();
        } else if (gameStage == 3){

        } else { //temporary holder for the "hunt enemies" code

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
                    //System.out.println("Trying to sense outside map");
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
                enemy_priority_override = false;
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
        myLocation = rc.getLocation();

        MapLocation dropTarget = null;
        int minDistance = 100000;
        for (Direction dir:directions){
            if (rc.canSenseLocation(HQLocation.add(dir))) {
                if (!rc.isLocationOccupied(HQLocation.add(dir))) { //no robot there, try to drop
                    if (dropTarget == null || myLocation.distanceSquaredTo(HQLocation.add(dir)) < minDistance){
                        dropTarget = HQLocation.add(dir);
                        minDistance = myLocation.distanceSquaredTo(dropTarget);
                    }
                }
            }
        }
        if (dropTarget == null){

            //System.out.println("dropTarget is null!");
            //wall complete, wait for HQ to send gameStage update
            return;
        }

        int distance = myLocation.distanceSquaredTo(dropTarget);

        if(distance <= 0) {
            //System.out.println("Drone too close!");
            tryMoveDirection(randomDirection());
        } else if(distance > 2) {

            //System.out.println("Drone farther than 2! Distance = "+distance+", holding_target = "+holding_target);
            //System.out.println("Target tile = ("+dropTarget.x+", "+dropTarget.y+")");
            tryMoveTowardsFavorLeft(dropTarget);
        } else { //if(distance <= 2 && distance > 0) { //not sure if they can drop on their own square
            if(rc.canDropUnit(myLocation.directionTo(dropTarget))) {
                //System.out.println("Drone dropping landscaper on wall!");
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
                //System.out.println("enemy_robot: " + cow.getType());
                if (cow.getType() == RobotType.COW) {
                    target = cow.getID();

                }
            }
        }

    }

    public void searchForLandscaper() throws GameActionException {
        RobotInfo[] allied_robots = rc.senseNearbyRobots(24, rc.getTeam());
        RobotInfo[] enemy_robots = rc.senseNearbyRobots(24, rc.getTeam().opponent());

        if (HQLocation == null){
            tryFindHQLocation();
            return;
        }

        if(enemy_robots != null) {
            for (RobotInfo enemy_robot : enemy_robots) {
                if (enemy_robot.getType() == RobotType.LANDSCAPER || enemy_robot.getType() == RobotType.MINER) {
                    target = enemy_robot.getID();
                    enemy_priority_override = true;
                    return;
                }
            }
        }

        if(allied_robots != null) {
            for (RobotInfo ally_robot : allied_robots) {
                if (ally_robot.getType() == RobotType.LANDSCAPER && !ally_robot.getLocation().isAdjacentTo(HQLocation)) {
                    if (target != -1){
                        RobotInfo target_info = null;
                        try{
                            target_info = rc.senseRobot(target);
                        }catch(GameActionException e)
                        {
                            target = -1;
                        }
                        if (rc.getLocation().distanceSquaredTo(ally_robot.getLocation()) < rc.getLocation().distanceSquaredTo(target_info.getLocation())){
                            target = ally_robot.getID();
                        }
                    } else {
                        target = ally_robot.getID();
                    }
                    //System.out.println("Drone found target! At ("+ally_robot.getLocation().x+", "+ally_robot.getLocation().y+")");
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
            enemy_priority_override = false;
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
            //System.out.println("Drone picking up enemy!");
            rc.pickUpUnit(target);
            holding_target = true;
        }

    }

    public void grabLandscaper() throws GameActionException
    {
        searchForLandscaper();
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
        if (target_info.getLocation().distanceSquaredTo(HQLocation) <= 2){ //don't grab from wall
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
            System.out.println("Drone picking up landscaper!");
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

    public void surroundHQ() throws GameActionException
    {
        if(current_ring_pos >= 16)
        {
            return;
        }

        MapLocation current_ring_destination = ring_positions.get(current_ring_pos);

        System.out.println("current_ring_destination: " + current_ring_destination);

        //if not at the ring position, do this, else do nothing if there already
        if(rc.getLocation().distanceSquaredTo(current_ring_destination) > 0)
        {

            //if you are not close enough to the current ring destination to sense
            if(!rc.canSenseLocation(current_ring_destination))
            {
                try{
                    tryMoveDirection(rc.getLocation().directionTo(current_ring_destination));
                } catch(GameActionException e)
                {
                    System.out.println("exception in surroundHQ 1");
                }
            }
            else //if you are close enough to sense
            {
                RobotInfo occupying_robot = rc.senseRobotAtLocation(current_ring_destination);

                if (occupying_robot != null) //if there's a robot in that position
                {
                    //if the robot is a delivery drone and on our team, update the position to be the next ring spot
                    if (occupying_robot.type == RobotType.DELIVERY_DRONE && occupying_robot.team == myTeam) {
                        current_ring_pos += 1;
                    }

                    moveRandom();

                } else //if the spot is empty
                {
                    //if this drone gets stuck trying to move towards the next spot, try to get around
                    Direction dir = rc.getLocation().directionTo(current_ring_destination);
                    MapLocation next_dest = myLocation.add(dir);
                    RobotInfo robot_obstacle = rc.senseRobotAtLocation(next_dest);
                    if(robot_obstacle != null)
                    {
                        if(dir == Direction.EAST)
                        {
                            dir = Direction.SOUTH;
                        }
                        else if(dir == Direction.SOUTH)
                        {
                            dir = Direction.EAST;
                        }
                    }
                    tryMoveDirection(dir);
                }
            }
        }

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
