package group10player;
import battlecode.common.*;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FulfillmentCenterTest {
    private RobotController RCtest = null;
    private FulfillmentCenter Fultest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = mock(RobotController.class);
        Fultest = new FulfillmentCenter(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        when(RCtest.getTeamSoup()).thenReturn(160);
        when(RCtest.canBuildRobot(RobotType.DELIVERY_DRONE,Direction.NORTHEAST)).thenReturn(true);
        for( int i = 0; i <= 1; i++) {
            Fultest.takeTurn();
            Fultest.numDrones++;
        }
        assertEquals(Fultest.numDrones,2);
    }

    @Test
    public void taketurntest2() throws GameActionException {
        when(RCtest.getTeamSoup()).thenReturn(50000);
        when(RCtest.canBuildRobot(RobotType.DELIVERY_DRONE,Direction.NORTHEAST)).thenReturn(true);
        for( int i = 0; i <= 1; i++) {
            Fultest.takeTurn();
            Fultest.numDrones++;
        }
        assertEquals(Fultest.numDrones,2);
    }
}
