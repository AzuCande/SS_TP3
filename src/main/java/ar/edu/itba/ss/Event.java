package ar.edu.itba.ss;

public class Event implements Comparable<Event> {
    //TODO: all null and 0??
    private double time;
    private Ball a;
    private Ball b;
    private int collisionCountA;
    private int collisionCountB;


    //VER: https://medium.com/nerd-for-tech/molecular-dynamics-simulation-of-hard-spheres-priority-queue-in-action-with-java-e5e513e57f76
    public Event(double time, Ball a, Ball b) {
        this.time = time;
        this.a = a;
        this.b = b;

        if (a != null && b != null) {
            this.collisionCountA = a.getCollisionCount();
            this.collisionCountB = b.getCollisionCount();
        } else if (a != null) {
            // Collision with a vertical wall
            this.collisionCountA = a.getCollisionCount();
        } else if (b != null) {
            // Collision with a horizontal wall
            this.collisionCountB = b.getCollisionCount();
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
    public int compareTo(Event x) {
        return Double.compare(this.time, x.getTime());
    }
    public boolean isValid() {
        //TODO:
        // return true if the event has not? been invalidated since creation,
        // and false if the event has been invalidated.
        if (a != null && a.getCollisionCount() != this.collisionCountA)
            return false;
        if (b != null && b.getCollisionCount() != this.collisionCountB)
            return false;

        if (Main.ballsInHoles.contains(a) || Main.ballsInHoles.contains(b))
            return false;

        return true;
    }

    public void updateTime(double diff) {
        this.time -= diff;
    }

}
