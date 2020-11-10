package group10player;
import battlecode.common.*;

public class DesignSchool extends Building {
    int numLandscapers = 0;
    int landscaperLimit = 3;
    public DesignSchool(RobotController rc) throws GameActionException
    {
        super(rc);
    }

    @Override
    public void takeTurn() throws GameActionException{
        super.takeTurn();
        if(numLandscapers < landscaperLimit)
        {
            if(rc.getTeamSoup() >= 155)
            {
                for(Direction dir : directions)
                {
                    if(tryBuild(RobotType.LANDSCAPER, dir))
                    {
                        numLandscapers++;
                        break;
                    }
                }
            }
        }

    }
}
