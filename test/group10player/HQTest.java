package group10player;

import battlecode.common.*;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class HQTest {

    private RobotController RCtest = null;
    private HQ HQtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        HQtest = new HQ(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.B);
        when(RCtest.getID()).thenReturn(821);
        when(RCtest.getTeamSoup()).thenReturn(70);
        when(RCtest.getRoundNum()).thenReturn(19999);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(true);

        for( int i = 0; i < 15; i++) {
            HQtest.takeTurn();
        }
    }

    @Test
    public void taketurntest2() throws GameActionException{
        HQtest.numMiners = 4;
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeamSoup()).thenReturn(301);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(true);
        for( int i = 0; i < 15; i++) {
            HQtest.takeTurn();
        }
    }


    @Test
    public void SendHQlocBlockchainTest() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,2));
        when(RCtest.canSubmitTransaction(new int[7],29)).thenReturn(true);
        HQtest.SendHQlocBlockchain();
        assertEquals(HQtest.hasSentLocation,false);

    }

    @Test
    public void ShooterTest() throws GameActionException{
        RobotInfo [] temp = new RobotInfo[1];;
        when(RCtest.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5))});
        when(RCtest.getTeam()).thenReturn(Team.B);
        when(RCtest.canShootUnit(1)).thenReturn(true);
        HQtest.Shooter();
    }
}
