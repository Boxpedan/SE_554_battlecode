package group10player;

import battlecode.common.*;

import org.junit.*;
import org.mockito.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MinerTest {

    private RobotController RCtest = null;
    private Miner Mtest = null;

    @Mock
    Robot rmock = mock(Robot.class);

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = mock(RobotController.class);
        Mtest = new Miner(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Mtest.myTeam = Team.A;
        RobotInfo [] temp = new RobotInfo[5];
        temp[0] = new RobotInfo(1, Team.A, RobotType.DESIGN_SCHOOL, 0, false, 0, 0, 0, new MapLocation(5, 5));
        temp[1] = new RobotInfo(2, Team.A, RobotType.REFINERY, 0, false, 0, 0, 0, new MapLocation(6, 6));
        temp[2] = new RobotInfo(3, Team.A, RobotType.FULFILLMENT_CENTER, 0, false, 0, 0, 0, new MapLocation(7, 7));
        temp[3] = new RobotInfo(4, Team.A, RobotType.NET_GUN, 0, false, 0, 0, 0, new MapLocation(8, 8));
        temp[4] = new RobotInfo(5, Team.A, RobotType.VAPORATOR, 0, false, 0, 0, 0, new MapLocation(9, 9));
        when(RCtest.senseNearbyRobots(Mtest.maxVisionSquared, Mtest.myTeam)).thenReturn(temp);
        Mtest.takeTurn();
    }


    @Test
    public void checkAndBuildTest1() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        when(RCtest.getTeamSoup()).thenReturn(500);
        when(RCtest.getTeam()).thenReturn(Team.A);
        RobotInfo [] temp = new RobotInfo[5];
        Mtest.myTeam = Team.A;
        Mtest.numNetgun = 3;
        temp[0] = new RobotInfo(1, Team.A, RobotType.DESIGN_SCHOOL, 0, false, 0, 0, 0, new MapLocation(5, 5));
        temp[1] = new RobotInfo(2, Team.A, RobotType.REFINERY, 0, false, 0, 0, 0, new MapLocation(6, 6));
        temp[2] = new RobotInfo(3, Team.A, RobotType.FULFILLMENT_CENTER, 0, false, 0, 0, 0, new MapLocation(7, 7));
        temp[3] = new RobotInfo(4, Team.A, RobotType.NET_GUN, 0, false, 0, 0, 0, new MapLocation(8, 8));
        temp[4] = new RobotInfo(5, Team.A, RobotType.VAPORATOR, 0, false, 0, 0, 0, new MapLocation(9, 9));
        when(RCtest.senseNearbyRobots(Mtest.maxVisionSquared, Mtest.myTeam)).thenReturn(temp);
        Mtest.checkAndBuild();
    }

    @Test
    public void checkAndBuildTest2() throws GameActionException{
        Mtest.myTeam = Team.A;
        Mtest.HQLocation = new MapLocation(3,3);
        when(RCtest.getLocation()).thenReturn(new MapLocation(4,4));
        RobotInfo [] temp = new RobotInfo[1];
        temp[0] = new RobotInfo(2, Team.A, RobotType.REFINERY, 0, false, 0, 0, 0, new MapLocation(5, 5));
        when(RCtest.senseNearbyRobots(Mtest.maxVisionSquared, Mtest.myTeam)).thenReturn(temp);
        when(RCtest.canBuildRobot(RobotType.REFINERY,Direction.NORTH)).thenReturn(true);
        when(RCtest.getTeamSoup()).thenReturn(1000);
        Mtest.checkAndBuild();
    }

    @Test
    public void checkAndBuildTest3() throws GameActionException{
        Mtest.myTeam = Team.A;
        Mtest.HQLocation = new MapLocation(3,3);
        when(RCtest.getLocation()).thenReturn(new MapLocation(4,4));
        RobotInfo [] temp = new RobotInfo[2];
        temp[0] = new RobotInfo(1, Team.A, RobotType.DESIGN_SCHOOL, 0, false, 0, 0, 0, new MapLocation(5, 5));
        temp[1] = new RobotInfo(2, Team.A, RobotType.REFINERY, 0, false, 0, 0, 0, new MapLocation(6, 6));
        when(RCtest.senseNearbyRobots(Mtest.maxVisionSquared, Mtest.myTeam)).thenReturn(temp);
        when(RCtest.canBuildRobot(RobotType.FULFILLMENT_CENTER,Direction.NORTH)).thenReturn(true);
        when(RCtest.getTeamSoup()).thenReturn(1000);
        Mtest.checkAndBuild();
    }

    @Test
    public void checkAndBuildTest4() throws GameActionException{
        Mtest.myTeam = Team.A;
        Mtest.HQLocation = new MapLocation(3,3);
        when(RCtest.getLocation()).thenReturn(new MapLocation(4,4));
        RobotInfo [] temp = new RobotInfo[3];
        temp[0] = new RobotInfo(1, Team.A, RobotType.DESIGN_SCHOOL, 0, false, 0, 0, 0, new MapLocation(5, 5));
        temp[1] = new RobotInfo(2, Team.A, RobotType.REFINERY, 0, false, 0, 0, 0, new MapLocation(6, 6));
        temp[2] = new RobotInfo(3, Team.A, RobotType.FULFILLMENT_CENTER, 0, false, 0, 0, 0, new MapLocation(7, 7));
        when(RCtest.senseNearbyRobots(Mtest.maxVisionSquared, Mtest.myTeam)).thenReturn(temp);
        when(RCtest.canBuildRobot(RobotType.NET_GUN,Direction.NORTH)).thenReturn(true);
        when(RCtest.getTeamSoup()).thenReturn(1000);
        Mtest.checkAndBuild();
    }


    @Test
    public void checkAndBuildTest5() throws GameActionException{
        Mtest.myTeam = Team.A;
        Mtest.HQLocation = new MapLocation(3,3);
        when(RCtest.getLocation()).thenReturn(new MapLocation(4,4));
        RobotInfo [] temp = new RobotInfo[4];
        Mtest.numNetgun = 3;
        temp[0] = new RobotInfo(1, Team.A, RobotType.DESIGN_SCHOOL, 0, false, 0, 0, 0, new MapLocation(5, 5));
        temp[1] = new RobotInfo(2, Team.A, RobotType.REFINERY, 0, false, 0, 0, 0, new MapLocation(6, 6));
        temp[2] = new RobotInfo(3, Team.A, RobotType.FULFILLMENT_CENTER, 0, false, 0, 0, 0, new MapLocation(7, 7));
        temp[3] = new RobotInfo(4, Team.A, RobotType.NET_GUN, 0, false, 0, 0, 0, new MapLocation(8, 8));
        when(RCtest.senseNearbyRobots(Mtest.maxVisionSquared, Mtest.myTeam)).thenReturn(temp);
        when(RCtest.canBuildRobot(RobotType.VAPORATOR,Direction.NORTH)).thenReturn(true);
        when(RCtest.getTeamSoup()).thenReturn(1000);
        Mtest.checkAndBuild();
    }



    @Test
    public void goMiningTest() throws GameActionException{
        Mtest.HQLocation = null;
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        Mtest.goMining();
    }
}
