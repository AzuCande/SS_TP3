package ar.edu.itba.ss;

public class Ball {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private final double radius;
    private final double mass;

    private final BallType type;
    private int collisionCount;

    public Ball(double x, double y, double vx, double vy, double radius, double mass, BallType type) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.type = type;
        this.collisionCount = 0;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setVx(double vx) {
        this.vx = vx;
    }
    public void setVy(double vy) {
        this.vy = vy;
    }
    public void setCollisionCount(Integer collisionCount) {
        this.collisionCount = collisionCount;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getVx() {
        return vx;
    }
    public double getVy() {
        return vy;
    }
    public double getRadius() {
        return radius;
    }
    public double getMass() {
        return mass;
    }
    public BallType getType() {
        return type;
    }
    public Integer getCollisionCount() {
        return collisionCount;
    }

    public boolean checkDistance(Ball b) {
        double distance = Math.pow(this.x + b.x, 2) + Math.pow(this.y + b.y, 2);
        return distance == Math.pow(this.radius + b.radius, 2);
    }

    public double collides(Ball b) {
        double[] deltaV =
                Utils.getDeltaV(this.getVx(), this.getVy(), b.getVx(), b.getVy());
        double[] deltaR =
                Utils.getDeltaR(this.getX(), this.getY(), b.getX(), b.getY());
        double sigma = this.getRadius() + b.getRadius();
        double deltaVMultiplyDeltaR = Utils.getScalarProduct(deltaV, deltaR);
        double deltaVPow2 = Utils.getScalarProduct(deltaV, deltaV);
        //TODO: check value:
        double d = Math.pow(deltaVMultiplyDeltaR, 2) -
                deltaVPow2 *
                (Utils.getScalarProduct(deltaR, deltaR) - Math.pow(sigma, 2));

        if (deltaVMultiplyDeltaR >= 0 || d < 0) {
            return Double.NaN;
        } else {
            return - (deltaVMultiplyDeltaR + Math.sqrt(d)) / deltaVPow2;
        }
    }

    public double collidesX() {
        double time = Double.NaN;
        if (this.getVx() > 0) {
            time = (Utils.tableWidth - this.getRadius() - this.getX()) / this.getVx();
        } else {
            time = (0 + this.getRadius() - this.getX()) / this.getVx();
        }
        return time;
    }
    
    public double collidesY() {
        double time = Double.NaN;
        if (this.getVy() > 0) {
            time = (Utils.tableHeight - this.getRadius() - this.getY()) / this.getVy();
        } else {
            time = (0 + this.getRadius() - this.getY()) / this.getVy();
        }
        return time;
    }

    public void bounceX() {
        this.setVx(-this.getVx());
    }

    public void bounceY() {
        this.setVy(-this.getVy());
    }

    public void bounce(Ball b) {
        double[] deltaV = Utils.getDeltaV(this.getVx(), this.getVy(), b.getVx(), b.getVy());
        double[] deltaR = Utils.getDeltaR(this.getX(), this.getY(), b.getX(), b.getY());

        double sigma = this.getRadius() + b.getRadius();

        double j = (2 * this.getMass() * b.getMass() * (Utils.getScalarProduct(deltaV, deltaR))) /
                (sigma * (this.getMass() + b.getMass()));

        double jx = j * deltaR[0] / sigma;
        double jy = j * deltaR[1] / sigma;

        this.vx += jx / this.mass;
        this.vy += jy / this.mass;
        b.vx -= jx / b.mass;
        b.vy -= jy / b.mass;
    }

    public void move(double dt) {
        this.x += this.vx * dt;
        this.y += this.vy * dt;
    }

}
