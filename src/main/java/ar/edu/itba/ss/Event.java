package ar.edu.itba.ss;

public class Event {
    //TODO: all null and 0??
    private double time;
    private Ball a;
    private Ball b;

    public Event(double time, Ball a, Ball b) {
        if (a != null && b != null) {
            // Collision between balls, a and b balls or one of them is a hole
        } else if (a != null) {
            // Collision with a vertical wall
        } else if (b != null) {
            // Collision with a horizontal wall
        } else {
            // redraw event???
        }
    }
    public double getTime() {
        return time;
    }
    public Ball getA() {
        return a;
    }
    public Ball getB() {
        return b;
    }
    //TODO: check if this is correct
    public int compareTo(Object x) {
        if (this.time < ((Event) x).getTime()) {
            return -1;
        } else if (this.time > ((Event) x).getTime()) {
            return 1;
        } else {
            return 0;
        }
    }
    public boolean wasSuperveningEvent() {
        //TODO:
        // return true if the event has been invalidated since creation,
        // and false if the event has been invalidated.
        return false;
    }
}
