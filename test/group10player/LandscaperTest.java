package group10player;

import battlecode.common.*;
import battlecode.common.RobotController;
import org.junit.*;
import org.mockito.*;


import java.util.Map;

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
        t[0] = new Transaction(1,array,2);
        LStest.myLocation = new MapLocation(1,1);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), false);
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        when(RCtest.canSenseLocation(new MapLocation(1,1))).thenReturn(true);
        when(RCtest.senseElevation(new MapLocation(1,1))).thenReturn(10);
        LStest.takeTurn();
    }

    @Test
    public void taketurntest3() throws GameActionException {
        Transaction temp = Mockito.mock(Transaction.class);
        int [] array = new int[5];
        array[0] = 265;
        array[1] = 000;
        array[2] = 1;
        array[3] = 1;
        array[4] = 0;
        Transaction [] t = new Transaction[1];
        t[0] = new Transaction(1,array,2);
        LStest.myLocation = new MapLocation(3,3);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), true);
        when(RCtest.getLocation()).thenReturn(new MapLocation(3,3));
        when(RCtest.canSenseLocation(new MapLocation(1,1))).thenReturn(true);
        when(RCtest.senseElevation(new MapLocation(1,1))).thenReturn(10);
        //when(LStest.tryMoveTowardsFavorRight(LStest.HQLocation)).thenReturn(false);
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
        t[0] = new Transaction(1,array,2);
        LStest.myLocation = new MapLocation(1,1);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), false);
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
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
        t[0] = new Transaction(1,array,2);
        LStest.myLocation = new MapLocation(1,1);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), false);
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
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
        t[0] = new Transaction(1,array,2);
        LStest.myLocation = new MapLocation(1,1);
        when(RCtest.getBlock(1)).thenReturn(t);
        when(temp.getMessage()).thenReturn(array);
        assertEquals(LStest.tryFindHQLocation(), false);
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        when(RCtest.canSenseLocation(new MapLocation(1,1))).thenReturn(false);
        when(RCtest.senseElevation(new MapLocation(1,1))).thenReturn(10);
        when(RCtest.canDigDirt(Direction.CENTER)).thenReturn(true);
        LStest.wallFinished = false;
        LStest.takeTurn();
    }


    @Test
    public void findHQTest() throws GameActionException {
        MapLocation hqLocation = new MapLocation(7,7);
        MapLocation lsLocation = new MapLocation(6,6);
        RobotInfo hqInfo = new RobotInfo(1, Team.B,RobotType.HQ,-1,false,-1, -1, -1, hqLocation);
        RobotInfo[] nearByRobots = {hqInfo};
        int elevation = 5;
        int sensorRadius = 24;

        //allow Landscaper to detect a nearby Robot that is HQ
        when(RCtest.senseNearbyRobots()).thenReturn(nearByRobots);

        //set Landscaper team to match HQ
        when(RCtest.getTeam()).thenReturn(Team.B);
        LStest.myTeam = Team.B;

        //set Landscaper location
        when(RCtest.getLocation()).thenReturn(lsLocation);
        LStest.setMyLocation();

        //set Landscaper elevation sensed equal to 5 to allow for HQ Locating
        when(RCtest.senseElevation(lsLocation)).thenReturn(elevation);
        when(RCtest.canSenseLocation(lsLocation)).thenReturn(true);
        LStest.myElevation = elevation;

        //set Landscaper sensor radius
        LStest.mySensorRadius = sensorRadius;

        //test canDig, expect True is returned
        boolean found = LStest.findHQ();
        assertTrue(found);

    }


    @Test
    public void digIfYouCanCanDig() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(6,6);
        MapLocation adjacent = new MapLocation(4,5);
        int elevation = 5;
        int sensorRadius = 24;

        //setHQLocation
        LStest.HQLocation = hqLocation;

        //set Landscaper sensor radius
        LStest.mySensorRadius = sensorRadius;

        //set Landscaper location
        when(RCtest.getLocation()).thenReturn(lsLocation);
        LStest.setMyLocation();


        //set Landscaper isAdjacentDirection
        when(RCtest.adjacentLocation(Direction.WEST)).thenReturn(adjacent);
        when(RCtest.isLocationOccupied(adjacent)).thenReturn(false);


        //set Landscaper elevation sensed equal to 5 to allow for HQ Locating
        when(RCtest.senseElevation(adjacent)).thenReturn(elevation);
        when(RCtest.canSenseLocation(adjacent)).thenReturn(true);
        LStest.myElevation = elevation;

        //Landscaper can dig to south
        when(RCtest.canDigDirt(Direction.WEST)).thenReturn(true);
        when(RCtest.canDigDirt(Direction.NORTHWEST)).thenReturn(true);

        //test digIfYouCan, expect True is returned
        boolean found = LStest.digIfYouCan();
        assertTrue(found);

    }

    @Test
    public void digIfYouCanTeamMate() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(6,6);
        MapLocation adjacent = new MapLocation(4,5);
        RobotInfo teammate = new RobotInfo(1, Team.B,RobotType.MINER,-1,false,-1, -1, -1, hqLocation);
        int sensorRadius = 24;

        //setHQLocation
        LStest.HQLocation = hqLocation;

        //set Landscaper sensor radius
        LStest.mySensorRadius = sensorRadius;

        //set Landscaper location
        when(RCtest.getLocation()).thenReturn(lsLocation);
        LStest.setMyLocation();


        //set Landscaper isAdjacentDirection
        when(RCtest.adjacentLocation(Direction.WEST)).thenReturn(adjacent);
        when(RCtest.isLocationOccupied(adjacent)).thenReturn(true);
        when(RCtest.senseRobotAtLocation(adjacent)).thenReturn(teammate);


        //test digIfYouCan, expect True is returned
        boolean found = LStest.digIfYouCan();
        assertFalse(found);


    }

    @Test
    public void digIfYouCanTooMuchDirt() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(6,6);

        int elevation = 5;
        int sensorRadius = 24;

        //setHQLocation
        LStest.HQLocation = hqLocation;

        //set Landscaper sensor radius
        LStest.mySensorRadius = sensorRadius;

        //set Landscaper location
        when(RCtest.getLocation()).thenReturn(lsLocation);
        LStest.setMyLocation();

        when(RCtest.getDirtCarrying()).thenReturn(LStest.maxCarry);


        //test digIfYouCan, expect True is returned
        boolean found = LStest.digIfYouCan();
        assertFalse(found);

    }




    @Test
    public void dropDirtIfYouCanBestLocation() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(4, 4);

        LStest.myLocation = lsLocation;
        LStest.HQLocation = hqLocation;

        when(RCtest.getLocation()).thenReturn(lsLocation);

        //return true if testing if can drop

        when(RCtest.canDepositDirt(Direction.SOUTH)).thenReturn(true);
        when(RCtest.canDepositDirt(Direction.WEST)).thenReturn(true);


        //test dropIfYouCan, expect True is returned
        boolean found = LStest.dropDirtIfYouCan();
        assertTrue(found);

    }

    @Test
    public void dropDirtIfYouCanSecondBestLocation() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(6, 6);

        LStest.myLocation = lsLocation;
        LStest.HQLocation = hqLocation;

        when(RCtest.getLocation()).thenReturn(lsLocation);

        //return true if testing if can drop
        when(RCtest.canDepositDirt(Direction.NORTH)).thenReturn(false);
        when(RCtest.canDepositDirt(Direction.EAST)).thenReturn(true);


        //test dropIfYouCan, expect True is returned
        boolean found = LStest.dropDirtIfYouCan();
        assertTrue(found);

    }

    @Test
    public void cantDropDirt() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(4, 5);

        LStest.myLocation = lsLocation;
        LStest.HQLocation = hqLocation;

        when(RCtest.getLocation()).thenReturn(lsLocation);

        //return true if testing if can drop

        when(RCtest.canDepositDirt(Direction.NORTH)).thenReturn(false);
        when(RCtest.canDepositDirt(Direction.SOUTH)).thenReturn(false);


        //test dropIfYouCan, expect True is returned
        boolean found = LStest.dropDirtIfYouCan();
        assertFalse(found);

    }



    @Test
    public void checkWallFinished() throws GameActionException {
        MapLocation hqLocation = new MapLocation(17,17);
        //setHQLocation
        //LStest.setHqLocation(hqLocation);
        LStest.HQLocation = hqLocation;

        //set up HQ wall test
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTH))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTHEAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTHWEST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTH))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTHWEST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTHEAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.EAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.WEST))).thenReturn(true);

        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTH))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTHEAST))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTHWEST))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTH))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTHWEST))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTHEAST))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.EAST))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.WEST))).thenReturn(LStest.getWallHeight());

        //test wallFinished, expect True is returned
        boolean found = LStest.checkWallFinished();
        assertTrue(found);
    }

    @Test
    public void checkWallNotFinished() throws GameActionException {
        MapLocation hqLocation = new MapLocation(17,17);

        //setHQLocation
        //LStest.setHqLocation(hqLocation);
        LStest.HQLocation = hqLocation;

        //set up HQ wall test
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTH))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTHEAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.SOUTHWEST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTH))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTHWEST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.NORTHEAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.EAST))).thenReturn(true);
        when(RCtest.canSenseLocation(hqLocation.add(Direction.WEST))).thenReturn(true);

        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTH))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTHEAST))).thenReturn(LStest.getWallHeight()-2);
        when(RCtest.senseElevation(hqLocation.add(Direction.SOUTHWEST))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTH))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTHWEST))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.NORTHEAST))).thenReturn(LStest.getWallHeight());
        when(RCtest.senseElevation(hqLocation.add(Direction.EAST))).thenReturn(LStest.getWallHeight()-3);
        when(RCtest.senseElevation(hqLocation.add(Direction.WEST))).thenReturn(LStest.getWallHeight());

        //test wallFinished, expect False is returned
        boolean found = LStest.checkWallFinished();
        assertFalse(found);
    }

    @Test
    public void cantWalkOnWall() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(5, 6);

        LStest.myLocation = lsLocation;
        LStest.HQLocation = hqLocation;

        when(RCtest.getLocation()).thenReturn(lsLocation);

        //return true if testing if can move

        when(RCtest.canMove(Direction.EAST)).thenReturn(false);
        when(RCtest.canMove(Direction.WEST)).thenReturn(false);


        //test dropIfYouCan, expect True is returned
        boolean found = LStest.tryWalkOnWall();
        assertFalse(found);

    }

    @Test
    public void canWalkOnWallFirstChoice() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(4, 6);

        LStest.myLocation = lsLocation;
        LStest.HQLocation = hqLocation;

        when(RCtest.getLocation()).thenReturn(lsLocation);

        //return true if testing if can drop

        when(RCtest.canMove(Direction.WEST)).thenReturn(true);
        when(RCtest.canMove(Direction.NORTH)).thenReturn(true);


        //test dropIfYouCan, expect True is returned
        boolean found = LStest.tryWalkOnWall();
        assertTrue(found);

    }

    @Test
    public void canWalkOnWallSecondChoice() throws GameActionException {
        MapLocation lsLocation = new MapLocation(5,5);
        MapLocation hqLocation = new MapLocation(5, 4);

        LStest.myLocation = lsLocation;
        LStest.HQLocation = hqLocation;

        when(RCtest.getLocation()).thenReturn(lsLocation);

        //return true if testing if can drop

        when(RCtest.canMove(Direction.WEST)).thenReturn(false);
        when(RCtest.canMove(Direction.EAST)).thenReturn(true);


        //test dropIfYouCan, expect True is returned
        boolean found = LStest.tryWalkOnWall();
        assertTrue(found);

    }

    @Test
    public void bestWallDirections(){
        MapLocation west = new MapLocation(4,5);
        Direction [] correct_west = {Direction.SOUTH, Direction.NORTH};
        MapLocation east = new MapLocation(6, 5);
        Direction [] correct_east = {Direction.NORTH, Direction.SOUTH};
        MapLocation north = new MapLocation(5,6);
        Direction [] correct_north = {Direction.WEST, Direction.EAST};
        MapLocation south = new MapLocation(5,4);
        Direction [] correct_south = {Direction.EAST, Direction.WEST};
        MapLocation northeast = new MapLocation(6,6);
        Direction [] correct_ne = {Direction.NORTH, Direction.EAST};
        MapLocation northwest = new MapLocation(4,6);
        Direction [] correct_nw = {Direction.WEST, Direction.NORTH};
        MapLocation southwest = new MapLocation(4,4);
        Direction [] correct_sw = {Direction.SOUTH, Direction.WEST};
        MapLocation southeast = new MapLocation(6,4);
        Direction [] correct_se = {Direction.EAST, Direction.SOUTH};

        LStest.myLocation = new MapLocation(5,5);
        LStest.HQLocation = west;
        Direction[] test_west = LStest.getBestWallDirections(LStest.myLocation);
        assertArrayEquals(test_west, correct_west );

        LStest.HQLocation = east;
        Direction[] test_east = LStest.getBestWallDirections(LStest.myLocation);
        assertArrayEquals(test_east, correct_east );

        LStest.HQLocation = north;
        Direction[] test_north = LStest.getBestWallDirections(LStest.myLocation);
        assertArrayEquals(test_north, correct_north );

        LStest.HQLocation = south;
        Direction[] test_south = LStest.getBestWallDirections(LStest.myLocation);
        assertArrayEquals(test_south, correct_south );

        LStest.HQLocation = southwest;
        Direction[] test_sw = LStest.getBestWallDirections(LStest.myLocation);
        assertArrayEquals(test_sw, correct_sw );

        LStest.HQLocation = southeast;
        Direction[] test_se = LStest.getBestWallDirections(LStest.myLocation);
        assertArrayEquals(test_se, correct_se );

        LStest.HQLocation = northwest;
        Direction[] test_nw = LStest.getBestWallDirections(LStest.myLocation);
        assertArrayEquals(test_nw, correct_nw );

        LStest.HQLocation = northeast;
        Direction[] test_ne = LStest.getBestWallDirections(LStest.myLocation);
        assertArrayEquals(test_ne, correct_ne );

    }

    @Test
    public void bestWallLocations(){
        MapLocation west = new MapLocation(4,5);
        MapLocation east = new MapLocation(6, 5);
        MapLocation north = new MapLocation(5,6);
        MapLocation south = new MapLocation(5,4);
        MapLocation northeast = new MapLocation(6,6);
        MapLocation northwest = new MapLocation(4,6);
        MapLocation southwest = new MapLocation(4,4);
        MapLocation southeast = new MapLocation(6,4);

        MapLocation [] correct_west = {south, north};
        MapLocation [] correct_east = {north, south};
        MapLocation [] correct_north = {west, east};
        MapLocation [] correct_south = {east, west};
        MapLocation [] correct_ne = {north, east};
        MapLocation [] correct_nw = {west, north};
        MapLocation [] correct_sw = {south, west};
        MapLocation [] correct_se = {east, south};

        LStest.myLocation = new MapLocation(5,5);
        LStest.HQLocation = west;
        MapLocation[] test_west = LStest.getBestWallLocations(LStest.myLocation);
        assertArrayEquals(test_west, correct_west );

        LStest.HQLocation = east;
        MapLocation[] test_east = LStest.getBestWallLocations(LStest.myLocation);
        assertArrayEquals(test_east, correct_east );

        LStest.HQLocation = north;
        MapLocation[] test_north = LStest.getBestWallLocations(LStest.myLocation);
        assertArrayEquals(test_north, correct_north );

        LStest.HQLocation = south;
        MapLocation [] test_south = LStest.getBestWallLocations(LStest.myLocation);
        assertArrayEquals(test_south, correct_south );

        LStest.HQLocation = southwest;
        MapLocation[] test_sw = LStest.getBestWallLocations(LStest.myLocation);
        assertArrayEquals(test_sw, correct_sw );

        LStest.HQLocation = southeast;
        MapLocation [] test_se = LStest.getBestWallLocations(LStest.myLocation);
        assertArrayEquals(test_se, correct_se );

        LStest.HQLocation = northwest;
        MapLocation [] test_nw = LStest.getBestWallLocations(LStest.myLocation);
        assertArrayEquals(test_nw, correct_nw );

        LStest.HQLocation = northeast;
        MapLocation [] test_ne = LStest.getBestWallLocations(LStest.myLocation);
        assertArrayEquals(test_ne, correct_ne );

    }
/* wip
    @Test
    public void checkElevationDifCantSenseZone() throws GameActionException{
        MapLocation west = new MapLocation(4,5);
        MapLocation north = new MapLocation(5,6);
        MapLocation mine = new MapLocation(5,5);
        Direction target = Direction.NORTH;

        when(RCtest.getLocation()).thenReturn(mine);
        when(LStest.myLocation.add(target)).thenReturn(north);
        when(RCtest.canSenseLocation(north)).thenReturn(false);


        boolean check = LStest.checkElevationDif(target);
        assertFalse(check);

    }
*/



}
