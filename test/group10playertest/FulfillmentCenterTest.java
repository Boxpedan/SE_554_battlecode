package group10playertest;
import battlecode.common.*;
import group10player.FulfillmentCenter;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class FulfillmentCenterTest {
    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private FulfillmentCenter Rtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        Rtest = new FulfillmentCenter(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Rtest.takeTurn();
    }
}
