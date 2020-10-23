package group10playertest;

import battlecode.common.*;
import group10player.HQ;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class HQTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private HQ HQtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
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
        HQtest.takeTurn();
    }

    @Test
    public void taketurntest2() throws GameActionException {
        when(RCtest.getType()).thenReturn(RobotType.HQ);
        assertEquals(RCtest.getType(), RobotType.HQ);
    }
}
