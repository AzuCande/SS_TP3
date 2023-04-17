package ar.edu.itba.ss;

import java.util.Objects;

public class Event implements Comparable<Event> {
    //TODO: all null and 0??
    private double time;
    private Ball a;
    private Ball b;
    private int collisionCountA;
    private int collisionCountB;
    private EventType eventType;


    //VER: https://medium.com/nerd-for-tech/molecular-dynamics-simulation-of-hard-spheres-priority-queue-in-action-with-java-e5e513e57f76
    public Event(double time, EventType eventType, Ball a, Ball b) {
        this.time = time;
        this.a = a;
        this.b = b;
        this.eventType = eventType;

        if (a != null) this.collisionCountA = a.getCollisionCount();
        else collisionCountA = -1;

        if (b != null) this.collisionCountB = b.getCollisionCount();
        else collisionCountB = -1;
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

    public void updateTime(double updatedTime) {
        this.time = updatedTime;

        if (a != null) this.collisionCountA = a.getCollisionCount();
        else collisionCountA = -1;

        if (b != null) this.collisionCountB = b.getCollisionCount();
        else collisionCountB = -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Double.compare(event.time, time) == 0 && collisionCountA == event.collisionCountA && collisionCountB == event.collisionCountB && Objects.equals(a, event.a) && Objects.equals(b, event.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, a, b, collisionCountA, collisionCountB);
    }

    @Override
    public String toString() {
        return "Event{" +
                "time=" + time +
                ", a=" + a +
                ", b=" + b +
                ", collisionCountA=" + collisionCountA +
                ", collisionCountB=" + collisionCountB +
                '}';
    }

    public EventType getEventType() {
        return eventType;
    }
}
