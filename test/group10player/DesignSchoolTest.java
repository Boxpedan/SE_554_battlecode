package group10player;

import battlecode.common.*;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DesignSchoolTest {

    private RobotController RCtest = null;
    private DesignSchool DStest = null;
    private Robot rhelper = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        rhelper = mock(Robot.class);
        DStest = new DesignSchool(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        when(RCtest.getTeamSoup()).thenReturn(160);
        for( int i = 0; i <= 15; i++) {
            DStest.takeTurn();
            DStest.numLandscapers++;
        }
        assertEquals(DStest.numLandscapers,16);
    }

    @Test
    public void taketurntest2() throws GameActionException {
        when(RCtest.getTeamSoup()).thenReturn(150);
        DStest.takeTurn();
        assertEquals(DStest.numLandscapers,0);
    }

    @Test
    public void taketurntest3() throws GameActionException {
        when(RCtest.getTeamSoup()).thenReturn(160);
        when(RCtest.canBuildRobot(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
        DStest.takeTurn();
        DStest.numLandscapers++;
        assertEquals(DStest.numLandscapers,1);

    }

    @Test
    public void taketurntest4() throws GameActionException {
        when(RCtest.getTeamSoup()).thenReturn(160);
        when(RCtest.canBuildRobot(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(false);
        DStest.takeTurn();
        assertEquals(DStest.numLandscapers,0);
    }
}
