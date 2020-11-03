package group10player;

import battlecode.common.*;
import group10player.Robot;
import group10player.RobotPlayer;
import group10player.Unit;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTest {
    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private Robot Rtest = null;
    private Unit Utest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        Rtest = Mockito.mock(Robot.class);
        Utest = new Unit(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Utest.takeTurn();
    }

    @Test
    public void tryMoveTowardsFavorRightTest1() throws GameActionException{
        assertEquals( Utest.tryMoveTowardsFavorRight(null), false);
    }

    @Test
    public void tryMoveTowardsFavorRightTest2() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        Utest.myLocation = new MapLocation(1,1);
        assertEquals( Utest.tryMoveTowardsFavorRight(temp), false);
    }

    @Test
    public void tryMoveTowardsFavorRightTest3() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        Utest.myLocation = new MapLocation(3,3);
        when(RCtest.canMove(Direction.NORTH)).thenReturn(true);
        when(RCtest.adjacentLocation(Direction.NORTH)).thenReturn(temp);
        when(RCtest.senseFlooding(temp)).thenReturn(false);
        assertEquals( Utest.tryMoveTowardsFavorRight(temp), true);
    }


    @Test
    public void testtryMoveDirection1 () throws GameActionException{

        assertEquals( Utest.tryMoveDirection(Direction.NORTH), false);

    }

    @Test
    public void testtryMoveDirection2 () throws GameActionException{
        assertEquals(Utest.tryMoveDirection(null), false);

    }

    @Test
    public void testtryMoveDirection3 () throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.canMove(Direction.NORTH)).thenReturn(true);
        when(RCtest.senseFlooding(temp)).thenReturn(true);
        assertEquals(Utest.tryMoveDirection(Direction.NORTH), true );

    }

    @Test
    public void testwalkRandom1() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.canMove(Direction.NORTH)).thenReturn(true);
        when(RCtest.senseFlooding(temp)).thenReturn(false);
        Utest.walkRandom();
    }

    @Test
    public void testwalkRandom2() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.canMove(Direction.NORTH)).thenReturn(true);
        when(RCtest.adjacentLocation(Direction.NORTH)).thenReturn(temp);
        when(RCtest.senseFlooding(temp)).thenReturn(true);
        Utest.walkRandom();
    }

    @Test
    public void testwalkRandom3() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.canMove(Direction.NORTH)).thenReturn(true);
        //when(RCtest.adjacentLocation(Direction.NORTH)).thenReturn(temp);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.NORTH))).thenReturn(false);
        Utest.walkRandom();
    }

}
