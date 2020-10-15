package group10player;
import battlecode.common.*;

public class HQ extends Building{
    int numMiners = 0;

    public HQ(RobotController rc) throws GameActionException {
        super(rc);

    }
    
    @Override
    public void takeTurn() throws GameActionException{

        if(numMiners < 1){
            for (Direction dir: Robot.directions){
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;

                }
            }
        }

        if(numMiners >=1 && rc.getTeamSoup() >= 300){
            for (Direction dir: Robot.directions){
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;
                }
            }
        }
        System.out.println("Total amount of soup is: "+rc.getTeamSoup());
    }
}