package group10playertest;

import battlecode.common.*;
import group10player.Landscaper;
import group10player.RobotPlayer;
import group10player.Robot;
import battlecode.common.RobotController;
import group10player.Unit;
import org.junit.*;
import org.mockito.*;
import org.scalactic.Or;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class LandscaperTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private Landscaper LStest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        LStest = new Landscaper(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
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

        when(RCtest.canDepositDirt(toDrop)).thenReturn(true);

        //test dropIfYouCan, expect True is returned
        boolean found = LStest.dropDirtIfYouCan(toDrop);
        assertTrue(found);

    }




}
