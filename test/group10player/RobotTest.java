package group10player;

import battlecode.common.*;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotTest {

    private RobotController RCtest = null;
    private Robot Robottest = null;

    @Before
    public void beforeEachTest() throws GameActionException {
        RCtest = Mockito.mock(RobotController.class);
        when(RCtest.getLocation()).thenReturn(new MapLocation(2,2));
        Robottest = new Robot(RCtest);
    }

    @Test
    public void taketurntest() throws GameActionException {
        Robottest.takeTurn();
    }

    @Test
    public void taketurntest2() throws GameActionException {
        Robottest.newUnitUpdated = false;
        Robottest.takeTurn();
    }

    @Test
    public void taketurntest3() throws GameActionException {
        Robottest.newUnitUpdated = false;
        when(RCtest.getTeamSoup()).thenReturn(5);
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
        array[0] = 265;
        array[1] = 0;
        array[2] = 1;
        array[3] = 1;
        array[4] = 0;
        Transaction [] t = new Transaction[1];
        t[0] = new Transaction(1,array,2);
        Robottest.myLocation = new MapLocation(2,2);
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

    @Test
    public void setMyLocationTest() throws GameActionException{
        when(RCtest.getLocation()).thenReturn(new MapLocation(1,1));
        Robottest.setMyLocation();
    }

    @Test
    public void trySendBlockchainMessageTest() throws GameActionException{
        Robottest.trySendBlockchainMessage(new int[3], 1);
    }

    @Test
    public void trySendBlockchainMessageTest2() throws GameActionException{
        Robottest.trySendBlockchainMessage(new int[7], 0);
    }

    @Test
    public void trySendBlockchainMessageTest3() throws GameActionException{
        int [] x  = new int[7];
        x[0] = 0;
        x[1] = 0;
        x[2] = 0;
        x[3] = 0;
        x[4] = 0;
        x[5] = 0;
        x[6] = 0;
        when(RCtest.canSubmitTransaction(x,1)).thenReturn(true);
        Robottest.trySendBlockchainMessage(x, 1);
    }

    @Test
    public void readBlockchainMessagesTest() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        a[0] = new Transaction(1,new int[6],1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest2() throws GameActionException{
        int round = 10;
        Transaction[] a = null;
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest3() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 0;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest4() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 1;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest5() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 2;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest6() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 3;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest7() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 4;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest8() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 5;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest9() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 6;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest10() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 7;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest11() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 10;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest12() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 100;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest13() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 199;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }

    @Test
    public void readBlockchainMessagesTest14() throws GameActionException{
        int round = 10;
        Transaction[] a = new Transaction[1];
        int [] array = new int[7];
        array[0] = 265;
        array[1] = 230;
        array[2] = 0;
        array[3] = 0;
        array[4] = 100;
        array[5] = 0;
        array[6] = 0;
        a[0] = new Transaction(1,array,1);
        when(RCtest.getBlock(round)).thenReturn(a);
        Robottest.readBlockchainMessages(round);
    }
}



