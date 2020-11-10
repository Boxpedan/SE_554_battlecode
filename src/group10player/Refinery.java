package group10player;
import battlecode.common.*;

public class Refinery extends Building{
    //this is to test Github
    boolean hasBroadcastedLocation;

    public Refinery(RobotController rc) throws GameActionException{
        super(rc);
        hasBroadcastedLocation = false;
        myLocation = rc.getLocation();
    }

    @Override
    public void takeTurn() throws GameActionException{
        super.takeTurn();
        /*if (!hasBroadcastedLocation){
            //broadcast location on Blockchain
            int [] refineryMessage = new int[7]; // formatted {159, 001, XPOS, YPOS, 000, 000, 000}
            for (int x = 0; x < 7; x++){
                refineryMessage[x] = 0;
            }
            refineryMessage[0] = teamMessageCode;
            refineryMessage[1] = 001;
            refineryMessage[2] = rc.getLocation().x;
            refineryMessage[3] = rc.getLocation().y;

            if (rc.canSubmitTransaction(refineryMessage, 11)){
                rc.submitTransaction(refineryMessage, 11);
                hasBroadcastedLocation = true;
            }
        }*/
    }
}
