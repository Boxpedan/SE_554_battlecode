package group10playertest;

import battlecode.common.*;
import group10player.Robot;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class RobotTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private Robot Robottest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        Robottest = new Robot(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Robottest.takeTurn();
    }
}

