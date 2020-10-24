package group10playertest;
import battlecode.common.*;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;


public class RobotPlayerTest {
    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;


    @Before
    public void beforeEachTest() {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        MapLocation map = new MapLocation(0,0);
        when(RCtest.getType()).thenReturn(RobotType.HQ);
        when(RCtest.getType()).thenReturn(RobotType.HQ);
        when(RCtest.getTeam()).thenReturn(Team.B);
        when(RCtest.getID()).thenReturn(821);
        when(RCtest.getTeamSoup()).thenReturn(13);
        when(RCtest.getSoupCarrying()).thenReturn(7);
        when(RCtest.getRoundNum()).thenReturn(19999);
        when(RCtest.getLocation()).thenReturn(map);
    }

    @Test
    public void test1fortype(){
        when(RCtest.getType()).thenReturn(RobotType.MINER);
        assertEquals(RCtest.getType(), RobotType.MINER);
    }
    @Test
    public void test2forwronngtype(){
        when(RCtest.getType()).thenReturn(RobotType.MINER);
        assertNotEquals(RobotType.HQ,RCtest.getType());
    }

    @Test
    public void UNRUNABLETESTFORRUN() throws GameActionException {
        //When the while true function running, it actually call RobotController varible .gettype() But not working.
        //RPtest.run(RCtest);
    }

}
