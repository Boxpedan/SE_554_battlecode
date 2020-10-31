package group10player;
import battlecode.common.*;

public class Landscaper extends Unit{
    //boolean seeFlood;
    //MapLocation floodedLocation;
    int storedDirt;
    int myElevation;
    int mySensorRadius;
    boolean wallFinished;
    //Direction directionFlooded;
    final int wallHeight = 10;  //height of wall to build around HQ

    public Landscaper(RobotController rc) throws GameActionException {
        super(rc);
        //seeFlood = rc.senseFlooding(myLocation);
        //floodedLocation = null;
        myElevation = rc.senseElevation(myLocation);
        storedDirt = 0;
        mySensorRadius = rc.getCurrentSensorRadiusSquared();
        HQDirection = null;
        wallFinished = false;
        //directionFlooded = null;
        tryFindHQLocation();
    }

    @Override
    public void takeTurn() throws GameActionException{
        myLocation = rc.getLocation();
        if (HQLocation == null){
            tryFindHQLocation();
            return;
        }
        if (rc.canSenseLocation(HQLocation) && rc.senseElevation(HQLocation) > initialHQElevation){
            if (myLocation.isAdjacentTo(HQLocation)){
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
                /*if (rc.getDirtCarrying() < 2){
                    if (!digIfYouCan()){
                        walkRandom();
                    }
                }*/
                boolean droppedDirt = this.dropDirtIfYouCan(HQDirection);
                if (!droppedDirt) {
                    if (!digIfYouCan()) {
                        walkRandom();
                    }
                }
            }/* else if(seeFlood) {
                System.out.println("Flooding Found...");
                boolean droppedDirt = this.dropDirtIfYouCan(directionFlooded);
                if(!droppedDirt){
                    if(!digIfYouCan()){
                        walkRandom();
                    }
                }
            }*/ else {
                //if(!findFlooding()){
                if (rc.getDirtCarrying() < 2) {
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


    //Look for flooding and if found update floodedLocation and move back one square
    /*public boolean findFlooding() throws GameActionException{
        for (Direction dir:directions){
            if(rc.canMove(dir)){
                MapLocation verifyLocation = rc.adjacentLocation(dir);
                seeFlood = rc.senseFlooding(verifyLocation);
                if(seeFlood) {
                    floodedLocation = verifyLocation;
                    MapLocation opposite = myLocation.subtract(dir);
                    directionFlooded = myLocation.directionTo(floodedLocation);
                    Direction oppositeDirection = myLocation.directionTo(opposite);
                    if (rc.canMove(oppositeDirection)) {
                        rc.move(oppositeDirection);
                        System.out.println("Flooding Found!");
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        System.out.println("No Flooding Found!");
        return false;
    }*/

    public boolean checkWallFinished() throws GameActionException{
        if (HQLocation == null){
            tryFindHQLocation();
            return false;
        }
        int sum = 0;
        for (Direction dir: directions){
            if (!rc.canSenseLocation(HQLocation.add(dir))){
                return false;
            }
            sum += rc.senseElevation(HQLocation.add(dir));
        }
        if (sum >= (wallHeight * 8)){
            wallFinished = true;
            return true;
        }
        return false;
    }

    //Look for HQ, update directionHQ
    public boolean findHQ() throws GameActionException{
        if (HQLocation == null){
            tryFindHQLocation();
            walkRandom();
            return false;
        }
        int minElevation = 1000;
        MapLocation verifyLocation;
        for (Direction dir: directions){
            //if(rc.canMove(dir)){
            verifyLocation = rc.adjacentLocation(dir);
            if(verifyLocation.isAdjacentTo(HQLocation)){
                if (rc.canSenseLocation(verifyLocation) && rc.senseElevation(verifyLocation) >= wallHeight){  //only raise wall to 10 high for now
                    continue;
                }
                if (rc.canSenseLocation(verifyLocation) && rc.senseElevation(verifyLocation) < minElevation){
                    HQDirection = dir;
                    minElevation = rc.senseElevation(verifyLocation);
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

    //Try to dig dirt and dig dirt if you can, give from highest elevation
    public boolean digIfYouCan() throws GameActionException{
        Direction maxElevationDir = null;
        int maxElevationAround = Integer.MIN_VALUE;
        boolean dontDig = false;

        MapLocation adjLocation;
        for (Direction dir: directions){
            dontDig = false;
            adjLocation = rc.adjacentLocation(dir);
            if (!rc.canSenseLocation(adjLocation)){
                System.out.println("My location is "+rc.getLocation().toString()+", I'm failing to see "+rc.adjacentLocation(dir).toString());
                continue;
            }
            if (!adjLocation.isAdjacentTo(HQLocation)/* && dir != directionFlooded*/) {
                for (Direction dir2: directions) {
                    if (!rc.canSenseLocation(adjLocation.add(dir2))){
                        System.out.println("My location is "+rc.getLocation().toString()+", I'm failing to see "+adjLocation.add(dir2).toString());
                        continue;
                    }
                    if (rc.senseFlooding(adjLocation.add(dir2))) {
                        dontDig = true; //don't dig if tile is adjacent to water
                        break;
                    }
                    if (adjLocation.distanceSquaredTo(HQLocation) <= 8){
                        dontDig = true; //don't dig from tiles within 2 tiles of HQ
                        break;
                    }
                }
                int dirElevation = rc.senseElevation(adjLocation);
                if (dirElevation > maxElevationAround && !dontDig && rc.canDigDirt(dir)){
                    maxElevationAround = dirElevation;
                    maxElevationDir = dir;
                }
            }
        }

        if(maxElevationDir != null){
            if (rc.canDigDirt(maxElevationDir)) {
                rc.digDirt(maxElevationDir);
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

}




