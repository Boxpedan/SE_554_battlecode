package group10player;

import battlecode.common.*;
import org.junit.*;
import org.mockito.*;

public class BuildingTest {

    private RobotController RCtest = null;
    private Building Btest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        Btest = new Building(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Btest.takeTurn();
    }
}
