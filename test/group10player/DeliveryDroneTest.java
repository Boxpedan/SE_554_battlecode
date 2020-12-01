package group10player;
//package group10player;

import battlecode.common.*;
import org.junit.*;
import org.mockito.*;
import org.scalactic.Or;
import scala.collection.immutable.Map;

import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class DeliveryDroneTest {

    private RobotController RCtest = null;
    private RobotPlayer RPtest = null;
    private DeliveryDrone DDtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        RPtest = Mockito.mock(RobotPlayer.class);
        when(RCtest.getTeam()).thenReturn(Team.A);
//        MapLocation drone_loc = new MapLocation(1,2);
//        when(RCtest.getLocation()).thenReturn(drone_loc);
        DDtest = new DeliveryDrone(RCtest);
    }

    @Test
    public void searchForEnemyTest() throws GameActionException {

        MapLocation drone_loc = new MapLocation(1,2);
        when(RCtest.getLocation()).thenReturn(drone_loc);
        MapLocation enemy_loc = new MapLocation(1, 1);
        RobotInfo enemy = new RobotInfo(123, Team.B, RobotType.MINER,  0, false, 0, 0, 1f, enemy_loc);
        RobotInfo[] enemies = new RobotInfo[]{enemy};

        when(RCtest.senseNearbyRobots(24, RCtest.getTeam().opponent())).thenReturn(enemies);

        DDtest.searchForEnemy();

//        assertEquals(DDtest.getTarget(), 123);
//        System.out.println("searchForEnemyTest");
    }

    @Test
    public void grabEnemyTest() throws GameActionException
    {
//        System.out.println("start grab test");

        MapLocation drone_loc = new MapLocation(1,2);
        when(RCtest.getLocation()).thenReturn(drone_loc);
        MapLocation enemy_loc = new MapLocation(1, 1);

        int target_id = 123;
        RobotInfo enemy = new RobotInfo(target_id, Team.B, RobotType.MINER,  0, false, 0, 0, 1f, enemy_loc);

        when(RCtest.senseRobot(target_id)).thenReturn(enemy);

//        System.out.println("after when senseRobot");
        //DDtest.setTarget(target_id);
        DDtest.target = target_id;

//        System.out.println("drone location: " + RCtest.getLocation());
//
        DDtest.grabEnemy();

        //assertEquals(true, RCtest.isCurrentlyHoldingUnit());
//        System.out.println("grabEnemyTest");
    }

    @Test
    public void takeTurnTest() throws GameActionException
    {
        MapLocation drone_loc = new MapLocation(1,2);
        when(RCtest.getLocation()).thenReturn(drone_loc);
        MapLocation enemy_loc = new MapLocation(1, 1);

        int target_id = 123;
        RobotInfo enemy = new RobotInfo(target_id, Team.B, RobotType.MINER,  0, false, 0, 0, 1f, enemy_loc);

        when(RCtest.senseRobot(target_id)).thenReturn(enemy);

        RobotInfo[] enemies = new RobotInfo[]{enemy};

//        System.out.println("enemies: " + enemies[0].getType());

        when(RCtest.senseNearbyRobots(24, Team.B)).thenReturn(enemies);

        DDtest.takeTurn();
//        System.out.println("takeTurnTest");
    }

    @Test
    public void takeTurnHoldingTest() throws GameActionException
    {
        MapLocation drone_loc = new MapLocation(1,2);
        when(RCtest.getLocation()).thenReturn(drone_loc);
        MapLocation enemy_loc = new MapLocation(1, 1);
        DDtest.know_water = true;
        DDtest.holding_target = false;

        int target_id = 123;
        DDtest.target = target_id;
        RobotInfo enemy = new RobotInfo(target_id, Team.B, RobotType.MINER,  0, false, 0, 0, 1f, enemy_loc);

        when(RCtest.senseRobot(target_id)).thenReturn(enemy);

        RobotInfo[] enemies = new RobotInfo[]{enemy};

//        System.out.println("enemies: " + enemies[0].getType());

        when(RCtest.senseNearbyRobots(24, Team.B)).thenReturn(enemies);

        DDtest.takeTurn();
//        System.out.println("takeTurnTest");
    }

    @Test
    public void takeTurnDropTest() throws GameActionException
    {
        MapLocation drone_loc = new MapLocation(1,2);
        when(RCtest.getLocation()).thenReturn(drone_loc);
        MapLocation enemy_loc = new MapLocation(1, 1);
        DDtest.know_water = true;
        DDtest.holding_target = true;
        DDtest.water_loc = new MapLocation(0,0);

        int target_id = 123;
        DDtest.target = target_id;
        RobotInfo enemy = new RobotInfo(target_id, Team.B, RobotType.MINER,  0, false, 0, 0, 1f, enemy_loc);

        when(RCtest.senseRobot(target_id)).thenReturn(enemy);

        RobotInfo[] enemies = new RobotInfo[]{enemy};

//        System.out.println("enemies: " + enemies[0].getType());

        when(RCtest.senseNearbyRobots(24, Team.B)).thenReturn(enemies);

        DDtest.takeTurn();
//        System.out.println("takeTurnTest");
    }


    @Test
    public void moveRandomTest() throws GameActionException
    {
        DDtest.moveRandom();
//        System.out.println("moveRandomTest");
    }

    @Test
    public void searchForWaterTest()
    {
//        Vector<MapLocation> loc_list = new Vector<>();
        MapLocation drone_loc = new MapLocation(3,3);
        when(RCtest.getLocation()).thenReturn(drone_loc);

        for(int i = 0; i < 7; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                if(i == 0 && j == 0)
                {
                    try {
                        when(RCtest.senseFlooding(new MapLocation(i, j))).thenReturn(true);
                    } catch (GameActionException e)
                    {
                        assertEquals("searchForWaterTest caught exception, failure 1", "failure");
                    }
                }
                else
                {
                    try {
                        when(RCtest.senseFlooding(new MapLocation(i, j))).thenReturn(false);
                    } catch (GameActionException e) {
                        assertEquals("searchForWaterTest caught exception, failure 2", "failure");
                    }
                }
            }
        }

        DDtest.searchForWater();

        assertEquals(true, DDtest.know_water);
        assertEquals(0, DDtest.water_loc.x);
        assertEquals(0, DDtest.water_loc.y);

//        System.out.println("searchForWaterTest");
    }

    @Test
    public void dropInWaterTest()
    {
        MapLocation drone_loc = new MapLocation(3,3);
        when(RCtest.getLocation()).thenReturn(drone_loc);
        DDtest.water_loc = new MapLocation(3,2);
        when(RCtest.canDropUnit(Direction.SOUTH)).thenReturn(true);

        try {
            DDtest.dropInWater();
        } catch (GameActionException e)
        {
            assertEquals("dropInWaterTest caught exception, failure", "failure");
        }

//        System.out.println("dropInWaterTest");
    }

    @Test
    public void dropInWaterDistZeroTest()
    {
        MapLocation drone_loc = new MapLocation(3,3);
        when(RCtest.getLocation()).thenReturn(drone_loc);
        DDtest.water_loc = new MapLocation(3,3);
        when(RCtest.canDropUnit(Direction.NORTH)).thenReturn(true);

        try {
            DDtest.dropInWater();
        } catch (GameActionException e)
        {
            assertEquals("dropInWaterTest caught exception, failure", "failure");
        }

//        System.out.println("dropInWaterTest");
    }

    @Test
    public void dropOnWallTest1() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        DDtest.dropOnWall();
    }

    @Test
    public void dropOnWallTest2() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        DDtest.HQLocation = new MapLocation(2,2);
        DDtest.dropOnWall();
    }

}
