package ar.edu.itba.ss.Float;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Ball {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final long id;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private final float radius;
    private final float mass;

    private final BallType type;
    private int collisionCount;

    private int colorR;
    private int colorG;
    private int colorB;

    private String symbol;

    public Ball(float x, float y, float vx, float vy, float radius,
                float mass, BallType type, int colorR, int colorG,
                int colorB, String symbol) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.type = type;
        this.collisionCount = 0;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.symbol = symbol;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public int getColorR() {
        return colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public String getSymbol() { return symbol; }


    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setVx(float vx) {
        this.vx = vx;
    }
    public void setVy(float vy) {
        this.vy = vy;
    }
    public void setCollisionCount(Integer collisionCount) {
        this.collisionCount = collisionCount;
    }
    public long getId() {
        return id;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getVx() {
        return vx;
    }
    public float getVy() {
        return vy;
    }
    public float getRadius() {
        return radius;
    }
    public float getMass() {
        return mass;
    }
    public BallType getType() {
        return type;
    }
    public Integer getCollisionCount() {
        return collisionCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ball ball = (Ball) o;
        return id == ball.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean checkDistance(Ball b) {
        float distance =
                (float) (Math.pow(this.x + b.x, 2) + Math.pow(this.y + b.y, 2));
        return distance == Math.pow(this.radius + b.radius, 2);
    }

    public float collides(Ball b) {
        float[] deltaV =
                Utils.getDeltaV(this.getVx(), this.getVy(), b.getVx(), b.getVy());
        float[] deltaR =
                Utils.getDeltaR(this.getX(), this.getY(), b.getX(), b.getY());
        float sigma = this.getRadius() + b.getRadius();
        float deltaVR = Utils.getScalarProduct(deltaV, deltaR);
        float deltaVV = Utils.getScalarProduct(deltaV, deltaV);
        float deltaRR = Utils.getScalarProduct(deltaR, deltaR);

        float d = deltaVR * deltaVR - deltaVV * (deltaRR - sigma * sigma);

        if (deltaVR >= 0 || d < 0) {
            return Float.NaN;
        }

        return (float) (- (deltaVR + Math.sqrt(d)) / deltaVV);
    }

    // return the duration of time until the invoking particle collides with a vertical wall
    public float collidesX() {
        float time = Float.NaN;
        if (this.getVx() > 0) {
            time = (Utils.tableWidth - this.getRadius() - this.getX()) /
                    this.getVx();
        } else if (this.getVx() < 0) {
            time = (0 + this.getRadius() - this.getX()) / this.getVx();
        }
        return time;
    }

    // return the duration of time until the invoking particle collides with a horizontal wall
    public float collidesY() {
        float time = Float.NaN;
        if (this.getVy() > 0) {
            time = (Utils.tableHeight - this.getRadius() - this.getY()) /
                    this.getVy();
        } else if (this.getVy() < 0) {
            time = (0 + this.getRadius() - this.getY()) / this.getVy();
        }
        return time;
    }

    // update the invoking particle to simulate it bouncing off a vertical wall
    public void bounceX() {
        this.vx = -vx;
        this.collisionCount++;
    }

    // update the invoking particle to simulate it bouncing off a horizontal wall
    public void bounceY() {
        this.vy = -vy;
        this.collisionCount++;
    }

    public void bounce(Ball b) {
        float[] deltaV = Utils.getDeltaV(this.getVx(), this.getVy(),
                b.getVx(), b.getVy());
        float[] deltaR = Utils.getDeltaR(this.getX(), this.getY(),
                b.getX(), b.getY());
        float deltaVR = Utils.getScalarProduct(deltaV, deltaR);

        float sigma = this.getRadius() + b.getRadius();

        float j = (2 * this.getMass() * b.getMass() * deltaVR) /(sigma * (this.getMass() + b.getMass()));

        float jx = (j * deltaR[0]) / sigma;
        float jy = (j * deltaR[1]) / sigma;

        this.vx += (jx / this.mass);
        this.vy += (jy / this.mass);

        b.vx -= (jx / b.mass);
        b.vy -= (jy / b.mass);

        this.collisionCount++;
        b.collisionCount++;
    }

    public void move(float dt) {
        this.x += this.vx * dt;
        this.y += this.vy * dt;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", vx=" + vx +
                ", vy=" + vy +
                ", radius=" + radius +
                ", mass=" + mass +
                ", type=" + type +
                ", collisionCount=" + collisionCount +
                '}';
    }

}
