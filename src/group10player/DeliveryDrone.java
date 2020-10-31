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
        super.takeTurn();

        if(target == -1) {

            tryMoveDirection(randomDirection());

            RobotInfo[] enemy_robots = rc.senseNearbyRobots(24, myTeam.opponent());

            if (enemy_robots.length > 0) {
                target = enemy_robots[0].getID();
            }
        }
        else
        {
            RobotInfo target_info = null;
            try{
                target_info = rc.senseRobot(target);
            }catch(GameActionException e)
            {
                target = -1;
                return;
            }

            tryMoveDirection(myLocation.directionTo(target_info.location));

        }




    }

    /**
     * Returns a random Direction.
     *
     * @return a random Direction
     */
    static Direction randomDirection() {
        return directions[(int) (Math.random() * directions.length)];
    }
}
