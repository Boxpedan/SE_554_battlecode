package group10player;
import battlecode.common.*;

public class HQ extends Building{
    int numMiners = 0;
    int limitMiners = 4;
    int MAX_LIMIT_MINER = 10;
    boolean hasSentLocation;

    public HQ(RobotController rc) throws GameActionException {
        super(rc);
        hasSentLocation = false;
    }
    
    @Override
    public void takeTurn() throws GameActionException{
        /*
        if (!hasSentLocation){
            int [] HQLocationMessage = new int[7]; // formatted {159, 000, XPOS, YPOS, ELE, 000, 000}
            for (int x = 0; x < 7; x++){
                HQLocationMessage[x] = 0;
            }
            HQLocationMessage[0] = teamMessageCode;
            HQLocationMessage[2] = rc.getLocation().x;
            HQLocationMessage[3] = rc.getLocation().y;
            HQLocationMessage[4] = rc.senseElevation(rc.getLocation()); //send initial elevation

            if (rc.canSubmitTransaction(HQLocationMessage, 29)){ //we're willing to pay 29, in order to make sure
                rc.submitTransaction(HQLocationMessage, 29); //that no strategies for flooding the blockchain on turn 1
                hasSentLocation = true;                         //make it so our message isn't sent.
            }
        }

        //shoot nearby delivery drones
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        if (!(nearbyRobots == null || nearbyRobots.length < 1)) {
            for (RobotInfo nearbyRobot : nearbyRobots) {
                if (nearbyRobot.type == RobotType.DELIVERY_DRONE && nearbyRobot.getTeam() == rc.getTeam().opponent()) {
                    if (rc.canShootUnit(nearbyRobot.getID())){
                        rc.shootUnit(nearbyRobot.getID());
                    }
                }
            }
        }
        */
        SendHQlocBlockchain();
        Shooter();
        if(numMiners < limitMiners){
            for (Direction dir: Robot.directions){
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;
                    if (numMiners >= limitMiners){
                        break;
                    }
                }
            }
        }
        System.out.println(numMiners+ " "+limitMiners+" "+rc.getTeamSoup());
        if(numMiners >= limitMiners&& numMiners <= MAX_LIMIT_MINER && rc.getTeamSoup() >= 300){
            for (Direction dir: Robot.directions){
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;
                    break;
                }
            }
        }
        System.out.println("Total amount of soup is: "+rc.getTeamSoup());

    }

    //Function to send the HQloc
    public void SendHQlocBlockchain() throws GameActionException {
        if (!hasSentLocation){
            int [] HQLocationMessage = new int[7]; // formatted {159, 000, XPOS, YPOS, ELE, 000, 000}
            for (int x = 0; x < 7; x++){
                HQLocationMessage[x] = 0;
            }
            HQLocationMessage[0] = teamMessageCode;
            HQLocationMessage[2] = rc.getLocation().x;
            HQLocationMessage[3] = rc.getLocation().y;
            HQLocationMessage[4] = rc.senseElevation(rc.getLocation()); //send initial elevation

            if (rc.canSubmitTransaction(HQLocationMessage, 29)){ //we're willing to pay 29, in order to make sure
                rc.submitTransaction(HQLocationMessage, 29); //that no strategies for flooding the blockchain on turn 1
                hasSentLocation = true;                         //make it so our message isn't sent.
            }
        }
    }

    public void Shooter() throws GameActionException {
        //shoot nearby delivery drones
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        if (!(nearbyRobots == null || nearbyRobots.length < 1)) {
            for (RobotInfo nearbyRobot : nearbyRobots) {
                if (nearbyRobot.type == RobotType.DELIVERY_DRONE && nearbyRobot.getTeam() == rc.getTeam().opponent()) {
                    if (rc.canShootUnit(nearbyRobot.getID())){
                        rc.shootUnit(nearbyRobot.getID());
                    }
                }
            }
        }
    }
}