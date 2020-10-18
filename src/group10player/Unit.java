package group10player;

import battlecode.common.*;

//Unit will extend Robot
public class Unit extends Robot{
    public Unit(RobotController rc) throws GameActionException {
        super(rc);
    }

    @Override
    public void takeTurn() throws GameActionException{

    }

    public boolean tryMoveTowards(MapLocation destination) throws GameActionException{
        Direction dirTowards = myLocation.directionTo(destination);
        for (int x = 0; x <= 8; x++) {
            if (rc.canMove(dirTowards) && !rc.senseFlooding(rc.adjacentLocation(dirTowards))) {
                rc.move(dirTowards);
                return true;
            } else {
                for (int y = 0; y < x; y++) { //rotate direction back and forth
                    if (x % 2 == 1) {
                        dirTowards = dirTowards.rotateRight();
                    } else {
                        dirTowards = dirTowards.rotateLeft();
                    }
                }
            }
        }
        return false;
    }

    public boolean tryMoveTowardsFavorRight(MapLocation destination) throws GameActionException{
        Direction dirTowards = myLocation.directionTo(destination);
        for (int x = 0; x <= 8; x++) {
            if (rc.canMove(dirTowards) && !rc.senseFlooding(rc.adjacentLocation(dirTowards))) {
                rc.move(dirTowards);
                return true;
            } else {//rotate direction right until you can move towards
                dirTowards = dirTowards.rotateRight();
            }
        }
        return false;
    }

    public boolean tryMoveDirection(Direction dirTowards) throws GameActionException{
        for (int x = 0; x <= 8; x++) {
            if (rc.canMove(dirTowards) && !rc.senseFlooding(rc.adjacentLocation(dirTowards))) {
                rc.move(dirTowards);
                return true;
            } else {
                for (int y = 0; y < x; y++) { //rotate direction back and forth
                    if (x % 2 == 1) {
                        dirTowards = dirTowards.rotateRight();
                    } else {
                        dirTowards = dirTowards.rotateLeft();
                    }
                }
            }
        }
        return false;
    }

    //walk in a random direction
    public void walkRandom() throws GameActionException{
        Direction dir = randomDirection();
        if (rc.canMove(dir)){
            if (!rc.senseFlooding(rc.adjacentLocation(dir))) {
                rc.move(dir);
            }
        }
    }
}
