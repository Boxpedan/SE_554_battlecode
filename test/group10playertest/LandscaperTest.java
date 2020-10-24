package group10playertest;

import battlecode.common.*;
import group10player.Landscaper;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import org.scalactic.Or;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class LandscaperTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private Landscaper LStest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        //LStest = new Landscaper(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        //LStest.takeTurn();
    }
}
