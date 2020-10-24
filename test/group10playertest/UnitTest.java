package group10playertest;

import battlecode.common.*;
import group10player.RobotPlayer;
import group10player.Unit;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class UnitTest {
    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private Unit Utest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        Utest = new Unit(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Utest.takeTurn();
    }

}
