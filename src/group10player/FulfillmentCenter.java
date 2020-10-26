package group10player;
import battlecode.common.*;

public class FulfillmentCenter extends Building{
    int numDrones = 0;
    int droneLimit = 1;
    public FulfillmentCenter(RobotController rc) throws GameActionException
    {
        super(rc);
    }

    @Override
    public void takeTurn() throws GameActionException{
        if(numDrones < droneLimit)
        {
            if(rc.getTeamSoup() >= 150)
            {
                for(Direction dir : directions)
                {
                    if(tryBuild(RobotType.DELIVERY_DRONE, dir))
                    {
                        numDrones++;
                        break;
                    }
                }
            }
        }

    }
}
