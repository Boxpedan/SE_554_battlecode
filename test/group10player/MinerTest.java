package group10player;

import battlecode.common.*;

import org.junit.*;
import org.mockito.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MinerTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private Miner Mtest = null;

    @Mock
    Robot rmock = mock(Robot.class);

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = mock(RobotController.class);
        RPtest = mock(RobotPlayer.class);
        Mtest = new Miner(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Mtest.takeTurn();
    }

    @Test
    public void checkAndBuildTest1() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        when(RCtest.getTeamSoup()).thenReturn(100);
        when(RCtest.getTeam()).thenReturn(Team.A);
        RobotInfo [] temp = new RobotInfo[3];
        temp[0] = new RobotInfo(1, Team.A, RobotType.DESIGN_SCHOOL, 0, false, 0, 0, 0, new MapLocation(5, 5));
        temp[1] = new RobotInfo(2, Team.A, RobotType.REFINERY, 0, false, 0, 0, 0, new MapLocation(6, 6));
        temp[2] = new RobotInfo(3, Team.A, RobotType.FULFILLMENT_CENTER, 0, false, 0, 0, 0, new MapLocation(7, 7));
        when(RCtest.senseNearbyRobots(34, Team.A)).thenReturn(new RobotInfo[]{new RobotInfo(1, Team.A, RobotType.DESIGN_SCHOOL, 0, false, 0, 0, 0, new MapLocation(5, 5))});
        Mtest.checkAndBuild();
    }

    @Test
    public void goMiningTest() throws GameActionException{
        rmock.HQLocation = null;
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        Mtest.goMining();
    }
}
