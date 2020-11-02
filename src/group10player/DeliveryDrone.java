package group10player;

import battlecode.common.*;

import java.awt.Robot;

public class DeliveryDrone extends Unit {

    int target = -1;

    public DeliveryDrone(RobotController rc) throws GameActionException {
        super(rc);

    }



    //drone will move around randomly until it finds an enemy
    //when it finds an enemy it will attempt to go pick them up, preventing them from doing anything
    @Override
    public void takeTurn() throws GameActionException {

//        myLocation = rc.getLocation();
//
//        //if no target move random and look for enemy
//        if(target == -1) {
//            searchForEnemy();
//        }
//        else //if have target move towards and try to pick up
//        {
//            grabEnemy();
//        }


    }


    public void searchForEnemy() throws GameActionException
    {
        System.out.println("start SearchForEnemy");

        Direction dir = randomDirection();
//        dir = Direction.EAST;
        System.out.println("trying to move");
        tryMoveDirection(dir);
        System.out.println("finish move");


        System.out.println("myTeam.opponent(): " + myTeam.opponent());
//        System.out.println("Team.B: " + Team.B);
//        System.out.println(myTeam.opponent() == getTeamOpponent());
//        RobotInfo[] enemy_robots = rc.senseNearbyRobots(24, myTeam.opponent());

//        System.out.println("enemy_robots: " + enemy_robots);

//        if(enemy_robots != null) {
//
//            for (int i = 0; i < enemy_robots.length; i++) {
//                RobotInfo enemy_robot = enemy_robots[i];
//                if (enemy_robot.getType() == RobotType.LANDSCAPER || enemy_robot.getType() == RobotType.MINER) {
//                    target = enemy_robot.getID();
//                }
//            }
//        }

    }

    public void grabEnemy() throws GameActionException
    {
//        System.out.println("start grabEnemy");
//
//        RobotInfo target_info = null;
//        try{
//            target_info = rc.senseRobot(target);
//        }catch(GameActionException e)
//        {
//            target = -1;
//            return;
//        }
//
//        System.out.println("after sensing target");
//
//        System.out.println("myLocation: " + myLocation);
//
//        int distance = myLocation.distanceSquaredTo(target_info.getLocation());
//
//        System.out.println("after distance calc");
//
//        System.out.println("team: " + myTeam + " distance: " + distance);
//
//        if(distance > 2)
//        {
//            Direction enemy_dir = myLocation.directionTo(target_info.location);
//
//            tryMoveDirection(enemy_dir);
//        }
//
//        System.out.println("after distance check");
//
//        System.out.println("canPickUpUnit: " + rc.canPickUpUnit(target));
//
//        if(rc.canPickUpUnit(target))
//        {
//            rc.pickUpUnit(target);
//        }
//
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

    /**
     * Returns a random Direction.
     *
     * @return a random Direction
     */
    static Direction randomDirection() {
        return directions[(int) (Math.random() * directions.length)];
    }

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
        return myTeam.opponent();
    }
}
