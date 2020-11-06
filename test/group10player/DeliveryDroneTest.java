package group10player;
//package group10player;

import battlecode.common.*;
import group10player.DeliveryDrone;
import group10player.Miner;
import group10player.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import org.scalactic.Or;
import scala.collection.immutable.Map;

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
        MapLocation drone_loc = new MapLocation(1,2);
        when(RCtest.getLocation()).thenReturn(drone_loc);
        DDtest = new DeliveryDrone(RCtest);
    }

    @Test
    public void searchForEnemyTest() throws GameActionException {


        MapLocation enemy_loc = new MapLocation(1, 1);
        RobotInfo enemy = new RobotInfo(123, Team.B, RobotType.MINER,  0, false, 0, 0, 1f, enemy_loc);
        RobotInfo[] enemies = new RobotInfo[]{enemy};
//
//        System.out.println("enemies: " + enemies[0].getType());

//        System.out.println("getting opponent: " + DDtest.getTeamOpponent());
//        DDtest.getTeamOpponent();
//        RCtest.getTeam().opponent()
//        DDtest.getTeamOpponent().opponent();

        when(RCtest.senseNearbyRobots(24, RCtest.getTeam().opponent())).thenReturn(enemies);

        DDtest.searchForEnemy();

//        assertEquals(DDtest.getTarget(), 123);
    }

    @Test
    public void grabEnemyTest() throws GameActionException
    {
//        System.out.println("start grab test");
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
    }

    @Test
    public void takeTurnTest() throws GameActionException
    {
        MapLocation enemy_loc = new MapLocation(1, 1);

        int target_id = 123;
        RobotInfo enemy = new RobotInfo(target_id, Team.B, RobotType.MINER,  0, false, 0, 0, 1f, enemy_loc);

        when(RCtest.senseRobot(target_id)).thenReturn(enemy);

        RobotInfo[] enemies = new RobotInfo[]{enemy};

//        System.out.println("enemies: " + enemies[0].getType());

        when(RCtest.senseNearbyRobots(24, Team.B)).thenReturn(enemies);

        DDtest.takeTurn();
    }

}