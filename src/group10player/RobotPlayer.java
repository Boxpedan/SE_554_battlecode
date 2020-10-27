package group10player;
import battlecode.common.*;

/** we used the framework from the battlecode lecture videos and github
 * https://github.com/battlecode/lectureplayer/blob/master/RobotPlayer.java
 */

public class RobotPlayer {
    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        Robot current_robot = null;

        switch (rc.getType()) {
            case HQ:
                current_robot = new HQ(rc);
                break;
            case MINER:
                current_robot = new Miner(rc);
                break;
            case REFINERY:
                current_robot = new Refinery(rc);
                break;
            case DESIGN_SCHOOL:
                current_robot = new DesignSchool(rc);
                break;
            case LANDSCAPER:
                current_robot = new Landscaper(rc);
                break;
            case FULFILLMENT_CENTER:
                current_robot = new FulfillmentCenter(rc);
                break;
        }

        while (true) {
            try {
                current_robot.takeTurn();
                Clock.yield();
            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            }
        }
    }
}
