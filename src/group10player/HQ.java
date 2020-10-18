package group10player;
import battlecode.common.*;

public class HQ extends Building{
    int numMiners = 0;
    int limitMiners = 4;
    int MAX_LIMIT_MINER = 10;

    public HQ(RobotController rc) throws GameActionException {
        super(rc);
    }
    
    @Override
    public void takeTurn() throws GameActionException{

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
}