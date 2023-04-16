package ar.edu.itba.ss;

import java.util.Objects;

public class Event implements Comparable<Event> {
    //TODO: all null and 0??
    private double time;
    private Ball a;
    private Ball b;

    //VER: https://medium.com/nerd-for-tech/molecular-dynamics-simulation-of-hard-spheres-priority-queue-in-action-with-java-e5e513e57f76
    public Event(double time, Ball a, Ball b) {
        this.time = time;
        this.a = a;
        this.b = b;
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
        if (a != null || b != null)
            return true;

        if (Main.ballsInHoles.contains(a) || Main.ballsInHoles.contains(b))
            return false;

        return true;
    }

    public void updateEvent(double time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Double.compare(event.time, time) == 0 && Objects.equals(a, event.a) && Objects.equals(b, event.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, a, b);
    }
}
