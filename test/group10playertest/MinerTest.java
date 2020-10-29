package group10playertest;

import battlecode.common.*;
import group10player.Miner;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import org.scalactic.Or;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class MinerTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private Miner Mtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        Mtest = new Miner(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Mtest.takeTurn();
    }
}
