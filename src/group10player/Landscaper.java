package group10player;
import battlecode.common.*;

public class Landscaper extends Unit{
    //boolean seeFlood;
    //MapLocation floodedLocation;
    //Direction directionFlooded;
    int maxCarry;
    int myElevation;
    int mySensorRadius;
    boolean wallFinished;
    final int wallHeight = 14;  //height of wall to build around HQ
    boolean onWall;
    int waitTime;



    public Landscaper(RobotController rc) throws GameActionException {
        super(rc);
        setMyLocation();
        if (rc.canSenseLocation(myLocation)){
            myElevation = rc.senseElevation(myLocation);
        }
        mySensorRadius = rc.getCurrentSensorRadiusSquared();
        HQDirection = null;
        wallFinished = false;
        onWall = false;
        waitTime = 3;
        tryFindHQLocation();
    }

    @Override
    public void takeTurn() throws GameActionException{
        super.takeTurn();
        myLocation = rc.getLocation();
        if (HQLocation == null){
            tryFindHQLocation();
            return;
        }
        if (rc.canSenseLocation(HQLocation) && rc.senseElevation(HQLocation) > initialHQElevation){
            if (myLocation.isAdjacentTo(HQLocation)){
                //System.out.println(myLocation + " "+ HQLocation + " "+rc.canDigDirt(myLocation.directionTo(HQLocation)));
                if (rc.canDigDirt(myLocation.directionTo(HQLocation))){
                    rc.digDirt(myLocation.directionTo(HQLocation));
                    System.out.println("Dug dirt out from HQ!");
                }else{
                    System.out.println("Could not dirt out from HQ!");
                }
            }else{
                if(!tryMoveTowardsFavorRight(HQLocation)){
                    walkRandom();
                }
            }
            return;
        }
        if (wallFinished || checkWallFinished()) {
            //here, we'll have instructions for if the wall around the HQ is finished.
            System.out.println("Wall is finished!");
            walkRandom();
        } else { //work on building wall
            if (findHQ()) {
                System.out.println("HQ Found...");
                boolean droppedDirt = this.dropDirtIfYouCan(HQDirection);
                if (!droppedDirt) {
                    if (!digIfYouCan()) {
                        walkRandom();
                    }
                }
            } else {
                if (rc.getDirtCarrying() < 1) {
                    if (!digIfYouCan()) {
                        if (!tryMoveTowardsFavorRight(HQLocation)) { //!moveTowardHQ()
                            walkRandom();
                        }
                    }
                } else {
                    if (!tryMoveTowardsFavorRight(HQLocation)) { //!moveTowardHQ()
                        walkRandom();
                    }
                }
                //}
            }
        }
    }


    public boolean checkWallFinished() throws GameActionException{
        if (HQLocation == null){
            tryFindHQLocation();
            return false;
        }
        for (Direction dir: directions){
            if (!rc.canSenseLocation(HQLocation.add(dir))){
                return false;
            }
            if (rc.senseElevation(HQLocation.add(dir)) < wallHeight){
                return false;
            }
        }
        return true;
    }

    //Look for HQ, update directionHQ
    public boolean findHQ() throws GameActionException{
        if (HQLocation == null){
            if(!tryFindHQLocation()) {
                walkRandom();
                return false;
            }
        }
        int minElevation = 1000;
        MapLocation verifyLocation;
        for (Direction dir: directions){
            //if(rc.canMove(dir)){
            myLocation = rc.getLocation();
            if(myLocation.isAdjacentTo(HQLocation) && rc.canSenseLocation(myLocation)){
                if(rc.senseElevation(myLocation) >= wallHeight){  //only raise wall to 10 high for now
                    continue;
                }
                if (rc.senseElevation(myLocation) < minElevation){
                    HQDirection = dir;
                    minElevation = rc.senseElevation(myLocation);
                }
            }
            //}    //end of:  if(rc.canMove(dir)){
        }
        if (minElevation < 1000){
            System.out.println("HQ found!");
            return true;
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



    //Try to dig dirt and dig dirt if you can, from highest elevation
    public boolean digIfYouCan() throws GameActionException{
        Direction minElevationDir = null;
        int minElevationAround = Integer.MAX_VALUE;
        boolean dontDig = false;

        MapLocation adjLocation;
        for (Direction dir: directions){
            dontDig = false;
            adjLocation = rc.adjacentLocation(dir);
            if(adjLocation != null) {
                if (!rc.canSenseLocation(adjLocation)) {
                    System.out.println("My location is " + rc.getLocation().toString() + ", I'm failing to see " + rc.adjacentLocation(dir).toString());
                    continue;
                }
                if (!adjLocation.isAdjacentTo(HQLocation)) {
                    for (Direction dir2 : directions) {
                        if (!rc.canSenseLocation(adjLocation.add(dir2))) {
                            System.out.println("My location is " + rc.getLocation().toString() + ", I'm failing to see " + adjLocation.add(dir2).toString());
                            continue;
                        }

                        if (adjLocation.distanceSquaredTo(HQLocation) <= 4) {
                            dontDig = true; //don't dig from tiles within 1 tiles of HQ
                            break;
                        }
                    }
                    int dirElevation = rc.senseElevation(adjLocation);
                    if (dirElevation <= minElevationAround && !dontDig && rc.canDigDirt(dir)) {
                        minElevationAround = dirElevation;
                        minElevationDir = dir;
                    }
                }
            }
        }

        if(minElevationDir != null){
            if (rc.canDigDirt(minElevationDir)) {
                rc.digDirt(minElevationDir);
                System.out.println("Landscaper digging");
                return true;
            }else{
                return false;
            }
        }
        else{
            System.out.println("Landscaper can't dig");
            return false;
        }
    }

    public boolean dropDirtIfYouCan(Direction toDrop) throws GameActionException {
        /*if(myLocation.isAdjacentTo(HQLocation)){
            return false;
        }*/
        if(rc.canDepositDirt(toDrop)){
            rc.depositDirt(toDrop);
            System.out.println("Landscaper dropping dirt");
            return true;
        }
        System.out.println("Landscaper unable to drop dirt");
        return false;
    }

    public int getWallHeight(){
        return wallHeight;
    }


}




