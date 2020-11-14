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
        when(RCtest.getTeamSoup()).thenReturn(550);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(true);
        for( int i = 0; i < 15; i++) {
            HQtest.takeTurn();
        }
    }

    @Test
    public void taketurntest3() throws GameActionException {
        HQtest.numMiners = 5;
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeamSoup()).thenReturn(300);
        HQtest.takeTurn();
    }

    @Test
    public void taketurntest4() throws GameActionException {
        HQtest.numMiners = 5;
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeamSoup()).thenReturn(550);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(false);
        HQtest.takeTurn();
    }

    @Test
    public void SendHQlocBlockchainTest() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,2));
        when(RCtest.senseElevation(new MapLocation(1,2))).thenReturn(3);
        int[] message = new int[7];
        message[0] = 265;
        message[1] = 0;
        message[2] = 1;
        message[3] = 2;
        message[4] = 3;
        message[5] = 0;
        message[6] = 0;
        when(RCtest.canSubmitTransaction(message,29)).thenReturn(true);
        HQtest.SendHQlocBlockchain();
    }

    @Test
    public void SendHQlocBlockchainTest2() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,2));
        when(RCtest.canSubmitTransaction(new int[7],29)).thenReturn(true);
        HQtest.SendHQlocBlockchain();
    }

    @Test
    public void ShooterTest() throws GameActionException{
        RobotInfo [] temp = new RobotInfo[1];;
        when(RCtest.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5))});
        when(RCtest.getTeam()).thenReturn(Team.B);
        when(RCtest.canShootUnit(1)).thenReturn(true);
        HQtest.Shooter();
    }

    @Test
    public void UpdateBlockchainTest() throws GameActionException{
        HQtest.HQUpdateRequested = true;
        HQtest.enemyHQLocation = new MapLocation(2,2);
        when(RCtest.getTeamSoup()).thenReturn(20);
        HQtest.UpdateBlockchain();
    }

    @Test
    public void UpdateBlockchainTest2() throws GameActionException{
        HQtest.HQUpdateRequested = true;
        HQtest.enemyHQLocation = null;
        when(RCtest.getTeamSoup()).thenReturn(20);
        HQtest.UpdateBlockchain();
    }

    @Test
    public void UpdateBlockchainTest3() throws GameActionException{
        HQtest.HQUpdateRequested = false;
        HQtest.enemyHQLocation = new MapLocation(1,1);
        when(RCtest.getRoundNum()).thenReturn(0);
        when(RCtest.getTeamSoup()).thenReturn(10);
        HQtest.UpdateBlockchain();
    }

    @Test
    public void UpdateBlockchainTest4() throws GameActionException{
        HQtest.HQUpdateRequested = false;
        HQtest.enemyHQLocation = new MapLocation(1,1);
        when(RCtest.getRoundNum()).thenReturn(0);
        when(RCtest.getTeamSoup()).thenReturn(0);
        HQtest.UpdateBlockchain();
    }
}
