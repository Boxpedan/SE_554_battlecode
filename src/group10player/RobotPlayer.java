package group10player;
import battlecode.common.*;

public class RobotPlayer {
    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        Robot current_robot = rc;

        turnCount = 0;

        switch (rc.getType()) {
            case HQ:
                current_robot = new HQ();
                break;
            case MINER:
                current_robot = new Miner();
                break;
            case REFINERY:
                current_robot = new Refinery();
                break;
            case DESIGN_SCHOOL:
                current_robot = new DesignSchool();
                break;
            case LANDSCAPER:
                current_robot = new Landscaper();
                break;
        }

        while (true) {
            try {
                Clock.yield();
            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            }
        }
    }
}
