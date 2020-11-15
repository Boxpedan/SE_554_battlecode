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
    public void taketurntest2() throws GameActionException {
        RobotInfo [] temp = new RobotInfo[1];
        temp[0] = new RobotInfo(1,Team.B,RobotType.MINER, 0, true,0,0,0,new MapLocation(5,5));
        when(RCtest.senseNearbyRobots()).thenReturn(temp);
        Utest.takeTurn();
    }

    @Test
    public void taketurntest3() throws GameActionException {
        RobotInfo [] temp = new RobotInfo[1];
        temp[0] = new RobotInfo(1,Team.B,RobotType.HQ, 0, true,0,0,0,new MapLocation(5,5));
        when(RCtest.senseNearbyRobots()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        Utest.takeTurn();
    }

    @Test
    public void taketurntest4() throws GameActionException {
        RobotInfo [] temp = new RobotInfo[1];
        temp[0] = new RobotInfo(1,Team.B,RobotType.HQ, 0, true,0,0,0,new MapLocation(5,5));
        when(RCtest.senseNearbyRobots()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        when(RCtest.getTeamSoup()).thenReturn(30);
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
        when(RCtest.canMove(Direction.NORTHEAST)).thenReturn(true);
        when(RCtest.canMove(Direction.EAST)).thenReturn(true);
        when(RCtest.canMove(Direction.SOUTHEAST)).thenReturn(true);
        when(RCtest.canMove(Direction.SOUTH)).thenReturn(true);
        when(RCtest.canMove(Direction.SOUTHWEST)).thenReturn(true);
        when(RCtest.canMove(Direction.WEST)).thenReturn(true);
        when(RCtest.canMove(Direction.NORTHWEST)).thenReturn(true);
        when(RCtest.adjacentLocation(Direction.NORTH)).thenReturn(temp);
        when(RCtest.adjacentLocation(Direction.NORTHEAST)).thenReturn(temp);
        when(RCtest.adjacentLocation(Direction.EAST)).thenReturn(temp);
        when(RCtest.adjacentLocation(Direction.SOUTHEAST)).thenReturn(temp);
        when(RCtest.adjacentLocation(Direction.SOUTH)).thenReturn(temp);
        when(RCtest.adjacentLocation(Direction.SOUTHWEST)).thenReturn(temp);
        when(RCtest.adjacentLocation(Direction.WEST)).thenReturn(temp);
        when(RCtest.adjacentLocation(Direction.NORTHWEST)).thenReturn(temp);
        Utest.walkRandom();
    }

    @Test
    public void testwalkRandom3() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.canMove(Direction.NORTH)).thenReturn(true);
        when(RCtest.canMove(Direction.NORTHEAST)).thenReturn(true);
        when(RCtest.canMove(Direction.EAST)).thenReturn(true);
        when(RCtest.canMove(Direction.SOUTHEAST)).thenReturn(true);
        when(RCtest.canMove(Direction.SOUTH)).thenReturn(true);
        when(RCtest.canMove(Direction.SOUTHWEST)).thenReturn(true);
        when(RCtest.canMove(Direction.WEST)).thenReturn(true);
        when(RCtest.canMove(Direction.NORTHWEST)).thenReturn(true);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.NORTH))).thenReturn(false);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.NORTHEAST))).thenReturn(false);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.EAST))).thenReturn(false);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.SOUTHEAST))).thenReturn(false);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.SOUTH))).thenReturn(false);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.SOUTHWEST))).thenReturn(false);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.WEST))).thenReturn(false);
        when(RCtest.senseFlooding(RCtest.adjacentLocation(Direction.NORTHWEST))).thenReturn(false);
        Utest.walkRandom();
    }

}
