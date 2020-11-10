package group10player;

import battlecode.common.*;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NetgunTest {
    private RobotController RCtest = null;
    private Netgun NGtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        NGtest = new Netgun(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        when(RCtest.getTeam()).thenReturn(Team.A);
        when(RCtest.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, Team.B)).thenReturn(new RobotInfo[]{new RobotInfo(12, Team.A, RobotType.DELIVERY_DRONE, 0, true, 0, 0, 0, new MapLocation(5, 5))});
        NGtest.takeTurn();
    }

    @Test
    public void taketurntest1() throws GameActionException {
        when(RCtest.getTeam()).thenReturn(Team.A);
        when(RCtest.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, Team.B)).thenReturn(null);
        NGtest.takeTurn();
    }

}
