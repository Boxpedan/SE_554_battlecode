package group10player;

import battlecode.common.*;
import group10player.Miner;
import group10player.Robot;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
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

    @Test
    public void tryBuildTest1() throws GameActionException{
        when(RCtest.isReady()).thenReturn(true);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(true);
        assertEquals(true, Robottest.tryBuild(RobotType.MINER,Direction.NORTH));
    }

    @Test
    public void tryBuildTest2() throws GameActionException{
        when(RCtest.isReady()).thenReturn(false);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(true);
        assertEquals(false, Robottest.tryBuild(RobotType.MINER,Direction.NORTH));
    }

    @Test
    public void tryFindHQLocationtest1() throws GameActionException{
        assertEquals(Robottest.tryFindHQLocation(), false);
    }

    @Test
    public void tryFindHQLocationtest2() throws GameActionException{
        //Transaction(int cost, int[] message, int id)
        Transaction temp = Mockito.mock(Transaction.class);
        int [] array = new int[5];
        array[0] = 0;
        array[1] = 000;
        array[2] = 1;
        array[3] = 1;
        array[4] = 0;
        Transaction [] t = new Transaction[1];
        t[0] = new Transaction(1,array,2);
        Robottest.myLocation = new MapLocation(1,1);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(Robottest.tryFindHQLocation(), true);
    }

    @Test
    public void tryFindHQLocationtest3() throws GameActionException{
        //RobotInfo(int ID, Team team, RobotType type, int dirtCarrying, boolean currentlyHoldingUnit, int heldUnitID, int soupCarrying, float cooldownTurns, MapLocation location)
        RobotInfo [] array = new RobotInfo[1];
        MapLocation HQloc = new MapLocation(2,2);
        array[0] = new RobotInfo(1, null, RobotType.HQ, 0, false, 10, 0, 0f, HQloc );
        Robottest.myLocation = new MapLocation(1,1);
        when(RCtest.senseNearbyRobots()).thenReturn(array);
        assertEquals(Robottest.tryFindHQLocation(), true);
    }

    @Test
    public void tryFindHQLocationtest4() throws GameActionException{
        //RobotInfo(int ID, Team team, RobotType type, int dirtCarrying, boolean currentlyHoldingUnit, int heldUnitID, int soupCarrying, float cooldownTurns, MapLocation location)
        RobotInfo [] array = new RobotInfo[1];
        MapLocation HQloc = new MapLocation(2,2);
        array[0] = new RobotInfo(1, null, RobotType.MINER, 0, false, 10, 0, 0f, HQloc );
        MapLocation test = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(test);
        when(RCtest.senseNearbyRobots()).thenReturn(array);
        assertEquals(Robottest.tryFindHQLocation(), false);
    }

}



