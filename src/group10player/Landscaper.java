package group10player;
import battlecode.common.*;

public class Landscaper extends Unit{
    boolean seeFlood;
    MapLocation floodedLocation;
    int storedDirt;
    int myElevation;
    int mySensorRadius;
    Direction directionFlooded;
    boolean foundHQ;

    public Landscaper(RobotController rc) throws GameActionException {
        super(rc);
        seeFlood = rc.senseFlooding(myLocation);
        foundHQ = false;
        floodedLocation = null;
        myElevation = rc.senseElevation(myLocation);
        storedDirt = 0;
        mySensorRadius = rc.getCurrentSensorRadiusSquared();
        HQDirection = null;
        directionFlooded = null;
        tryFindHQLocation();
    }

    @Override
    public void takeTurn() throws GameActionException{
        myLocation = rc.getLocation();
        if(foundHQ || findHQ()){
            System.out.println("HQ Found...");
            boolean droppedDirt = this.dropDirtIfYouCan(HQDirection);
            if(!droppedDirt){
                if(!digIfYouCan()){
                    walkRandom();
                }
            }
        } else if(seeFlood) {
            System.out.println("Flooding Found...");
            boolean droppedDirt = this.dropDirtIfYouCan(directionFlooded);
            if(!droppedDirt){
                if(!digIfYouCan()){
                    walkRandom();
                }
            }
        }
        else{
            if(!findFlooding()){
                if(!tryMoveTowards(HQLocation)){ //!moveTowardHQ()
                    walkRandom();
                }
            }
        }
    }


    //Look for flooding and if found update floodedLocation and move back one square
    public boolean findFlooding() throws GameActionException{
        for (Direction dir:directions){
            if(rc.canMove(dir)){
                MapLocation verifyLocation = rc.adjacentLocation(dir);
                seeFlood = rc.senseFlooding(verifyLocation);
                if(seeFlood) {
                    floodedLocation = verifyLocation;
                    MapLocation opposite = myLocation.subtract(dir);
                    directionFlooded = myLocation.directionTo(floodedLocation);
                    Direction oppositeDirection = myLocation.directionTo(opposite);
                    rc.move(oppositeDirection);
                    System.out.println("Flooding Found!");
                }
            }
        }
        System.out.println("No Flooding Found!");
        return false;
    }

    //Look for HQ, update foundHQ if adjacent to neighbor, and update directionHQ
    public boolean findHQ() throws GameActionException{
        if (HQLocation == null){
            walkRandom();
            tryFindHQLocation();
        }
        for (Direction dir: directions){
            if(rc.canMove(dir)){
                MapLocation verifyLocation = rc.adjacentLocation(dir);
                if(verifyLocation.isAdjacentTo(HQLocation)){
                    HQDirection = dir;
                    System.out.println("HQ found!");
                    return true;
                }
            }
        }
        System.out.println("HQ not found...");
        return false;
    }

    //Move in a random direction
    /*public void moveRandomly() throws GameActionException{
        Direction dir = randomDirection();
        if (rc.canMove(dir)){
            System.out.println("Landscaper moving randomly");
           rc.move(dir);
        }
    }*/

    //Move toward HQ, move toward HQ if possible
    /*public boolean moveTowardHQ() throws GameActionException{
       HQDirection = myLocation.directionTo(HQLocation);
       if(rc.canMove(HQDirection)){
           rc.move(HQDirection);
           System.out.println("Landscaper moving toward HQ");
           return true;
       }

       return false;
    }*/

    //Try to dig dirt and dig dirt if you can, give from highest elevation
    public boolean digIfYouCan() throws GameActionException{
        Direction maxElevationDir = null;
        int maxElevationAround = Integer.MIN_VALUE;

        for (Direction dir: directions){
            if (dir != HQDirection && dir != directionFlooded) {
                int dirElevation = rc.senseElevation(rc.adjacentLocation(dir));
                if (dirElevation > maxElevationAround){
                    maxElevationAround = dirElevation;
                    maxElevationDir = dir;
                }
            }
        }

        if(maxElevationDir != null){
            rc.digDirt(maxElevationDir);
            System.out.println("Landscaper digging");
            return true;
        }
        else{
            System.out.println("Landscaper can't dig");
            return false;
        }
    }

    public boolean dropDirtIfYouCan(Direction toDrop) throws GameActionException {
        if(myLocation.isAdjacentTo(HQLocation)){
            return false;
        }
        if(rc.canDepositDirt(toDrop)){
            rc.depositDirt(toDrop);
            System.out.println("Landscaper dropping dirt");
            return true;
        }
        System.out.println("Landscaper unable to drop dirt");
        return false;
    }

}




