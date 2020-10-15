package group10player;
import battlecode.common.*;

public class DesignSchool extends Building {
    int numLandscappers = 0;
    int landscapperLimit = 1;
    public DesignSchool(RobotController rc) throws GameActionException
    {
        super(rc);
    }

    @Override
    public void takeTurn() throws GameActionException{
        if(numLandscappers < landscapperLimit)
        {
            if(rc.getTeamSoup() >= 150)
            {
                for(Direction dir : directions)
                {
                    if(tryBuild(RobotType.LANDSCAPER, dir))
                    {
                        numLandscappers++;
                        break;
                    }
                }
            }
        }

    }
}
