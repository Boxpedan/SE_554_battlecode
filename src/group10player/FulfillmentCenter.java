package group10player;
import battlecode.common.*;

public class FulfillmentCenter extends Building{
    int numDrones = 0;
    int droneLimit = 2;
    boolean freeBuild;
    public FulfillmentCenter(RobotController rc) throws GameActionException
    {
        super(rc);
        freeBuild = false;
    }

    @Override
    public void takeTurn() throws GameActionException{
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
        if (rc.getTeamSoup() > 1000){
            freeBuild = true;
        }
        if (freeBuild){
            if(rc.getTeamSoup() >= 200)
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
