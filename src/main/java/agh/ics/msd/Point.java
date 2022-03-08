package agh.ics.msd;

import java.util.ArrayList;
import java.util.Random;


public class Point {
    private ArrayList<Point> neighbors;
    private int currentState;
    private int nextState;
    private int numStates = 6;

    public Point() {
        currentState = 0;
        nextState = 0;
        neighbors = new ArrayList<Point>();
    }

    public void clicked() {
        currentState=(++currentState)%numStates;
    }

    public int getState() {
        return currentState;
    }

    public void setState(int s) {
        currentState = s;
    }

    public void setNextState(int s){this.nextState = s; }

    public void calculateNewState(int type) {
        if (type == 0){
        if (this.getState() == 0)
        {
            if (this.getActiveNeighbors() == 3)
            {
                this.setNextState(1);
            }
        }
        else if (this.getState() == 1)
        {
            if (this.getActiveNeighbors() !=2 && this.getActiveNeighbors() != 3)
            {
                this.setNextState(0);
            }
        }}
        else if (type == 1)
        {
            if (this.currentState  > 0)
            {
                this.setNextState(currentState - 1);
            }
            else if (this.currentState == 0 && this.getActiveNeighbors() == 1)
            {
                this.setNextState(6);
            }
        }
        //TODO: insert logic which updates according to currentState and
        //number of active neighbors
    }

    public void changeState() {
        currentState = nextState;
    }

    public void addNeighbor(Point nei) {
        neighbors.add(nei);
    }

    public void drop()
    {
        Random random = new Random();
        int a = random.nextInt(20);
        if (a == 1)
        {
            this.currentState = 6;
        }
    }

    public int getActiveNeighbors()
    {
        int activeNeighborsNumber = 0;
        for (Point neighbor: this.neighbors)
        {
            if (neighbor.currentState >= 1)
            {
                activeNeighborsNumber ++;
            }
        }
        return activeNeighborsNumber;
    }
    //TODO: write method counting all active neighbors of THIS point
}
