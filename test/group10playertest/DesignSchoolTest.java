package group10playertest;

import battlecode.common.*;
import group10player.DesignSchool;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import org.scalactic.Or;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class DesignSchoolTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private DesignSchool DStest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        DStest = new DesignSchool(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        DStest.takeTurn();
    }

}
