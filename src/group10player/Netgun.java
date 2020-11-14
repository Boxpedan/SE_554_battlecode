package group10player;
import battlecode.common.*;

public class Netgun extends Building{
    public Netgun(RobotController rc) throws GameActionException {
        super(rc);
    }

    @Override
    public void takeTurn() throws GameActionException{
        super.takeTurn();
        shootDrone();
    }


    public void shootDrone() throws GameActionException{
        RobotInfo[] enemies = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, rc.getTeam().opponent());
        if(enemies == null) return;
        for (RobotInfo x : enemies){
            if(x.type == RobotType.DELIVERY_DRONE && x.getHeldUnitID() > 0){
                if(rc.canShootUnit(x.ID)){
                    rc.shootUnit(x.ID);
                    System.out.println("Shoots enemy drone with ID: "+ x.ID);
                }
            }
        }
    }

}
