package group10player;

import battlecode.common.*;
import group10player.Refinery;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class RefineryTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private Refinery Rtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        Rtest = new Refinery(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Rtest.takeTurn();
    }
}
