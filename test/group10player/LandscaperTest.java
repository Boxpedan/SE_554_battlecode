package group10player;

import battlecode.common.*;
import battlecode.common.RobotController;
import org.junit.*;
import org.mockito.*;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LandscaperTest {

    private RobotController RCtest = null;
    private Landscaper LStest = null;
    private Robot rhelper = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = mock(RobotController.class);
        rhelper = mock(Robot.class);
        LStest = new Landscaper(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        LStest.takeTurn();
    }

    @Test
    public void taketurntest2() throws GameActionException {
        Transaction temp = Mockito.mock(Transaction.class);
        int [] array = new int[5];
        array[0] = 0;
        array[1] = 000;
        array[2] = 1;
        array[3] = 1;
        array[4] = 0;
        Transaction [] t = new Transaction[1];
        t[0] = new Transaction(1,array,1);
        MapLocation test = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(test);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), true);
        when(RCtest.canSenseLocation(new MapLocation(1,1))).thenReturn(true);
        when(RCtest.senseElevation(new MapLocation(1,1))).thenReturn(10);
        LStest.takeTurn();
    }

    @Test
    public void taketurntest3() throws GameActionException {
        Transaction temp = Mockito.mock(Transaction.class);
        int [] array = new int[5];
        array[0] = 0;
        array[1] = 000;
        array[2] = 1;
        array[3] = 1;
        array[4] = 0;
        Transaction [] t = new Transaction[1];
        t[0] = new Transaction(1,array,1);
        MapLocation test = new MapLocation(3,3);
        when(RCtest.getLocation()).thenReturn(test);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), true);
        when(RCtest.canSenseLocation(new MapLocation(1,1))).thenReturn(true);
        when(RCtest.senseElevation(new MapLocation(1,1))).thenReturn(10);
        LStest.takeTurn();
    }

    @Test
    public void taketurntest4() throws GameActionException {
        Transaction temp = Mockito.mock(Transaction.class);
        int [] array = new int[5];
        array[0] = 0;
        array[1] = 000;
        array[2] = 1;
        array[3] = 1;
        array[4] = 0;
        Transaction [] t = new Transaction[1];
        t[0] = new Transaction(1,array,1);
        MapLocation test = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(test);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), true);
        when(RCtest.canSenseLocation(new MapLocation(1,1))).thenReturn(true);
        when(RCtest.senseElevation(new MapLocation(1,1))).thenReturn(10);
        when(RCtest.canDigDirt(Direction.CENTER)).thenReturn(true);
        LStest.takeTurn();
    }

    @Test
    public void taketurntest5() throws GameActionException {
        Transaction temp = Mockito.mock(Transaction.class);
        int [] array = new int[5];
        array[0] = 0;
        array[1] = 000;
        array[2] = 1;
        array[3] = 1;
        array[4] = 0;
        Transaction [] t = new Transaction[1];
        t[0] = new Transaction(1,array,1);
        MapLocation test = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(test);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), true);
        when(RCtest.canSenseLocation(new MapLocation(1,1))).thenReturn(false);
        when(RCtest.senseElevation(new MapLocation(1,1))).thenReturn(10);
        when(RCtest.canDigDirt(Direction.CENTER)).thenReturn(true);
        LStest.wallFinished = true;
        LStest.takeTurn();
    }

    @Test
    public void taketurntest6() throws GameActionException {
        Transaction temp = Mockito.mock(Transaction.class);
        int [] array = new int[5];
        array[0] = 0;
        array[1] = 000;
        array[2] = 1;
        array[3] = 1;
        array[4] = 0;
        Transaction [] t = new Transaction[1];
        t[0] = new Transaction(1,array,1);
        MapLocation test = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(test);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), true);
        when(RCtest.canSenseLocation(new MapLocation(1,1))).thenReturn(false);
        when(RCtest.senseElevation(new MapLocation(1,1))).thenReturn(10);
        when(RCtest.canDigDirt(Direction.CENTER)).thenReturn(true);
        LStest.wallFinished = false;
        LStest.takeTurn();
    }


    @Test
    public void findHQTest() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(7,7);
        MapLocation adjacent = new MapLocation(6,6);
        RobotInfo hqInfo = new RobotInfo(1, Team.B,RobotType.HQ,-1,false,-1, -1, -1, hqLocation);
        RobotInfo[] nearByRobots = {hqInfo};
        int elevation = 5;
        int sensorRadius = 24;

        //allow Landscaper to detect a nearby Robot that is HQ
        when(RCtest.senseNearbyRobots()).thenReturn(nearByRobots);

        //set Landscaper team to match HQ
        when(RCtest.getTeam()).thenReturn(Team.B);
        LStest.setMyTeam();

        //set Landscaper location
        when(RCtest.getLocation()).thenReturn(lsLocation);
        LStest.setMyLocation();

        //set Landscaper can move true if northwest
        when(RCtest.canMove(Direction.NORTHEAST)).thenReturn(true);
        //set Landscaper isAdjacentDirection
        when(RCtest.adjacentLocation(Direction.NORTHEAST)).thenReturn(adjacent);

        //set Landscaper elevation sensed equal to 5 to allow for HQ Locating
        when(RCtest.senseElevation(adjacent)).thenReturn(elevation);
        when(RCtest.canSenseLocation(adjacent)).thenReturn(true);
        LStest.setMyElevation(elevation);

        //set Landscaper sensor radius
        LStest.setMySensorRadius(sensorRadius);

        //test canDig, expect True is returned
        boolean found = LStest.findHQ();
        assertTrue(found);

    }


    @Test
    public void digIfYouCanCanDig() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(17,17);
        MapLocation adjacent = new MapLocation(7,7);
        int elevation = 5;
        int sensorRadius = 24;

        //setHQLocation
        LStest.setHqLocation(hqLocation);

        //set Landscaper sensor radius
        LStest.setMySensorRadius(sensorRadius);

        //set Landscaper location
        when(RCtest.getLocation()).thenReturn(lsLocation);
        LStest.setMyLocation();

        //set Landscaper can move true if northwest
        when(RCtest.canMove(Direction.NORTHEAST)).thenReturn(true);
        //set Landscaper isAdjacentDirection
        when(RCtest.adjacentLocation(Direction.NORTHEAST)).thenReturn(adjacent);

        //set Landscaper elevation sensed equal to 5 to allow for HQ Locating
        when(RCtest.senseElevation(adjacent)).thenReturn(elevation);
        when(RCtest.canSenseLocation(adjacent)).thenReturn(true);
        when(RCtest.canSenseLocation(adjacent.add(Direction.SOUTH))).thenReturn(true);
        LStest.setMyElevation(elevation);

        //Landscaper can dig to south
        when(RCtest.canDigDirt(Direction.SOUTH)).thenReturn(true);
        when(RCtest.canDigDirt(Direction.NORTHEAST)).thenReturn(true);

        //test digIfYouCan, expect True is returned
        boolean found = LStest.digIfYouCan();
        assertTrue(found);

    }

    @Test
    public void dropDirtIfYouCan() throws GameActionException {
        Direction toDrop = Direction.NORTHEAST;

        //return true if testing if can drop
        when(RCtest.canDepositDirt(toDrop)).thenReturn(true);

        //test dropIfYouCan, expect True is returned
        boolean found = LStest.dropDirtIfYouCan(toDrop);
        assertTrue(found);

    }

    @Test
    public void checkWallFinished() throws GameActionException {
        MapLocation hqLocation = new MapLocation(17,17);
        //setHQLocation
        LStest.setHqLocation(hqLocation);

        //set up HQ wall test
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTH))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTHEAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTHWEST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTH))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTHWEST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTHEAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.EAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.WEST))).thenReturn(true);

        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTH))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTHEAST))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTHWEST))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTH))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTHWEST))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTHEAST))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.EAST))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.WEST))).thenReturn(10);

        //test wallFinished, expect True is returned
        boolean found = LStest.checkWallFinished();
        assertTrue(found);
    }

    @Test
    public void checkWallNotFinished() throws GameActionException {
        MapLocation hqLocation = new MapLocation(17,17);

        //setHQLocation
        LStest.setHqLocation(hqLocation);

        //set up HQ wall test
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTH))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTHEAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTHWEST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTH))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTHWEST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTHEAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.EAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.WEST))).thenReturn(true);

        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTH))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTHEAST))).thenReturn(7);
        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTHWEST))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTH))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTHWEST))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTHEAST))).thenReturn(10);
        when(RCtest.senseElevation(hqLocation.add(Direction.EAST))).thenReturn(8);
        when(RCtest.senseElevation(hqLocation.add(Direction.WEST))).thenReturn(10);

        //test wallFinished, expect False is returned
        boolean found = LStest.checkWallFinished();
        assertFalse(found);
    }


}
