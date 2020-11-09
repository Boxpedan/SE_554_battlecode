package group10player;

import battlecode.common.*;
import org.junit.*;
import org.mockito.*;

public class VaporatorTest {
    private RobotController RCtest = null;
    private Vaporator Vtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        Vtest = new Vaporator(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Vtest.takeTurn();
    }
}
