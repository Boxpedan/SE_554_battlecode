package group10player;
import battlecode.common.*;

public class FulfillmentCenter extends Building{
    int numDrones = 0;
    int droneLimit = 3;
    public FulfillmentCenter(RobotController rc) throws GameActionException
    {
        super(rc);
    }

    @Override
    public void takeTurn() throws GameActionException{
        super.takeTurn();
        if(numDrones < droneLimit)
        {
            if(rc.getTeamSoup() >= 155)
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
