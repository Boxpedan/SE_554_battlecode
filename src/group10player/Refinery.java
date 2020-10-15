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
        if (!hasBroadcastedLocation){
            //broadcast location on Blockchain
        }
    }
}
