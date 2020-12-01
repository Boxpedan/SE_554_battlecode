package group10player;

import battlecode.common.*;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class HQTest {

    private RobotController RCtest = null;
    private HQ HQtest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        HQtest = new HQ(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.B);
        when(RCtest.getID()).thenReturn(821);
        when(RCtest.getTeamSoup()).thenReturn(70);
        when(RCtest.getRoundNum()).thenReturn(19999);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(true);

        for( int i = 0; i < 15; i++) {
            HQtest.takeTurn();
        }
    }

    @Test
    public void taketurntest2() throws GameActionException{
        HQtest.numMiners = 4;
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeamSoup()).thenReturn(550);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(true);
        for( int i = 0; i < 15; i++) {
            HQtest.takeTurn();
        }
    }

    @Test
    public void taketurntest3() throws GameActionException {
        HQtest.numMiners = 5;
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeamSoup()).thenReturn(300);
        HQtest.takeTurn();
    }

    @Test
    public void taketurntest4() throws GameActionException {
        HQtest.numMiners = 5;
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeamSoup()).thenReturn(550);
        when(RCtest.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(false);
        HQtest.takeTurn();
    }

    @Test
    public void SendHQlocBlockchainTest() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,2));
        when(RCtest.senseElevation(new MapLocation(1,2))).thenReturn(3);
        int[] message = new int[7];
        message[0] = 265;
        message[1] = 0;
        message[2] = 1;
        message[3] = 2;
        message[4] = 3;
        message[5] = 0;
        message[6] = 0;
        when(RCtest.canSubmitTransaction(message,29)).thenReturn(true);
        HQtest.SendHQlocBlockchain();
    }

    @Test
    public void SendHQlocBlockchainTest2() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,2));
        when(RCtest.canSubmitTransaction(new int[7],29)).thenReturn(true);
        HQtest.SendHQlocBlockchain();
    }

    @Test
    public void ShooterTest() throws GameActionException{
        RobotInfo [] temp = new RobotInfo[1];;
        when(RCtest.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5))});
        when(RCtest.getTeam()).thenReturn(Team.B);
        when(RCtest.canShootUnit(1)).thenReturn(true);
        HQtest.Shooter();
    }

    @Test
    public void UpdateBlockchainTest() throws GameActionException{
        HQtest.HQUpdateRequested = true;
        HQtest.enemyHQLocation = new MapLocation(2,2);
        when(RCtest.getTeamSoup()).thenReturn(20);
        HQtest.UpdateBlockchain();
    }

    @Test
    public void UpdateBlockchainTest2() throws GameActionException{
        HQtest.HQUpdateRequested = true;
        HQtest.enemyHQLocation = null;
        when(RCtest.getTeamSoup()).thenReturn(20);
        HQtest.UpdateBlockchain();
    }

    @Test
    public void UpdateBlockchainTest3() throws GameActionException{
        HQtest.HQUpdateRequested = false;
        HQtest.enemyHQLocation = new MapLocation(1,1);
        when(RCtest.getRoundNum()).thenReturn(0);
        when(RCtest.getTeamSoup()).thenReturn(10);
        HQtest.UpdateBlockchain();
    }

    @Test
    public void UpdateBlockchainTest4() throws GameActionException{
        HQtest.HQUpdateRequested = false;
        HQtest.enemyHQLocation = new MapLocation(1,1);
        when(RCtest.getRoundNum()).thenReturn(0);
        when(RCtest.getTeamSoup()).thenReturn(0);
        HQtest.UpdateBlockchain();
    }

    @Test
    public void UpdateGameStageTest1() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTH))).thenReturn(null);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTHEAST))).thenReturn(null);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTHWEST))).thenReturn(null);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.WEST))).thenReturn(null);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.EAST))).thenReturn(null);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTHWEST))).thenReturn(null);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTHEAST))).thenReturn(null);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTH))).thenReturn(null);
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest2() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTH))).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTHEAST))).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTHWEST))).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.WEST))).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.EAST))).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTHWEST))).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTHEAST))).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTH))).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest3() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTH))).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTHEAST))).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.NORTHWEST))).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.WEST))).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.EAST))).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTHWEST))).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTHEAST))).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        when(RCtest.senseRobotAtLocation(temp.add(Direction.SOUTH))).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest4() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(null);
        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest5() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest6() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest7() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(null);
        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest8() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest9() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest10() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        for(int in = 0; in < 3; in++){
            MapLocation temp2 = new MapLocation(temp.x-2, temp.y-1+in);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(null);
        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest11() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        for(int in = 0; in < 3; in++){
            MapLocation temp2 = new MapLocation(temp.x-2, temp.y-1+in);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));

        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest12() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        for(int in = 0; in < 3; in++){
            MapLocation temp2 = new MapLocation(temp.x-2, temp.y-1+in);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));

        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest13() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        for(int in = 0; in < 3; in++){
            MapLocation temp2 = new MapLocation(temp.x-2, temp.y-1+in);
            MapLocation temp3 = new MapLocation(temp.x+2, temp.y-1+in);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(null);
        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest14() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        for(int in = 0; in < 3; in++){
            MapLocation temp2 = new MapLocation(temp.x-2, temp.y-1+in);
            MapLocation temp3 = new MapLocation(temp.x+2, temp.y-1+in);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.LANDSCAPER, 0, false, 0, 0, 0, new MapLocation(5, 5)));

        }
        HQtest.UpdateGameStage();
    }

    @Test
    public void UpdateGameStageTest15() throws GameActionException{
        HQtest.gameStage = 2;
        MapLocation temp = new MapLocation(5,5);
        when(RCtest.getLocation()).thenReturn(temp);
        when(RCtest.getTeam()).thenReturn(Team.A);
        for(int in = 0; in < 5; in++) {
            MapLocation temp2 = new MapLocation(temp.x-2+in, temp.y+2);
            MapLocation temp3 = new MapLocation(temp.x-2+in, temp.y-2);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
        }
        for(int in = 0; in < 3; in++){
            MapLocation temp2 = new MapLocation(temp.x-2, temp.y-1+in);
            MapLocation temp3 = new MapLocation(temp.x+2, temp.y-1+in);
            when(RCtest.canSenseLocation(temp2)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp2)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));
            when(RCtest.canSenseLocation(temp3)).thenReturn(true);
            when(RCtest.senseRobotAtLocation(temp3)).thenReturn(new RobotInfo(1, Team.A, RobotType.DELIVERY_DRONE, 0, false, 0, 0, 0, new MapLocation(5, 5)));

        }
        HQtest.UpdateGameStage();
    }
}
