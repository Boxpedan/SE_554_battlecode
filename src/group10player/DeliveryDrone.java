package group10player;

import battlecode.common.*;
import scala.collection.Map;

import java.awt.Robot;

public class DeliveryDrone extends Unit {

    int target = -1;
    boolean holding_target = false;
    MapLocation water_loc = null;
    boolean know_water = false;

    public DeliveryDrone(RobotController rc) throws GameActionException {
        super(rc);
//        System.out.println("initialize delivery drone");
//        System.out.println("constructor myLocation: " + myLocation);
//        System.out.println("constructor myLocation: " + rc.getLocation());
    }



    //drone will move around randomly until it finds an enemy
    //when it finds an enemy it will attempt to go pick them up, preventing them from doing anything
    @Override
    public void takeTurn() throws GameActionException {
        super.takeTurn();

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
            grabEnemy();
        }
        else
        {
            if(know_water)
            {
                dropInWater();
            }
            else
            {
                moveRandom();
                searchForWater();
            }

        }

//        System.out.println("holding_target: " + holding_target);

    }

    private void moveRandom() throws GameActionException{
        Direction dir = randomDirection();
//        dir = Direction.SOUTH;
        tryMoveDirection(dir);
    }

    private void searchForWater() {
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

                }

                if(sense_return) //if the tile is flooded
                {
                    water_loc = loc;
                    know_water = true;
                    System.out.println("water found");

                }
            }
        }
    }

    private void dropInWater() throws GameActionException {
        MapLocation drone_loc = rc.getLocation();
        Direction water_dir = drone_loc.directionTo(water_loc);
        int distance = drone_loc.distanceSquaredTo(water_loc);
        if(distance <= 0)
        {
            tryMoveDirection(randomDirection());
        }
        if(distance > 2)
        {
            tryMoveDirection(water_dir);
        }
        if(distance <= 2 && distance > 0) //not sure if they can drop on their own square
        {
            if(rc.canDropUnit(water_dir))
            {
                rc.dropUnit(water_dir);
                holding_target = false;
                target = -1;
            }
        }
    }


    public void searchForEnemy()
    {
//        System.out.println("start SearchForEnemy");

//        Direction dir = randomDirection();
//        dir = Direction.EAST;
//        System.out.println("trying to move");
//        tryMoveDirection(dir);
//        System.out.println("finish move");

//
//        System.out.println("myTeam.opponent(): " + rc.getTeam().opponent());
//        System.out.println("Team.B: " + Team.B);
//        System.out.println(myTeam.opponent() == getTeamOpponent());
//        RobotInfo[] enemy_robots = rc.senseNearbyRobots(24, myTeam.opponent());
        RobotInfo[] enemy_robots = rc.senseNearbyRobots(24, rc.getTeam().opponent());
        RobotInfo[] cows = rc.senseNearbyRobots(24, Team.NEUTRAL);
//
//        System.out.println("enemy_robots: " + enemy_robots);

        if(enemy_robots != null) {

            for (RobotInfo enemy_robot : enemy_robots) {
                System.out.println("enemy_robot: " + enemy_robot.getType());
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

//        System.out.println("after sensing target");

//        System.out.println("myLocation: " + rc.getLocation());

        int distance = rc.getLocation().distanceSquaredTo(target_info.getLocation());

//        System.out.println("after distance calc");

//        System.out.println("team: " + myTeam + " distance: " + distance);

        if(distance > 2)
        {
            Direction enemy_dir = rc.getLocation().directionTo(target_info.location);

            tryMoveDirection(enemy_dir);
        }

//        System.out.println("after distance check");

//        System.out.println("canPickUpUnit: " + rc.canPickUpUnit(target));

        if(rc.canPickUpUnit(target))
        {
            rc.pickUpUnit(target);
            holding_target = true;
        }

//        System.out.println("after pickup");

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
