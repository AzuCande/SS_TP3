package ar.edu.itba.ss;

public class Bocha {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double radio;
    private double masa;

    public Bocha(double x, double y, double vx, double vy, double radio, double masa) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radio = radio;
        this.masa = masa;
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

    public double getRadio() {
        return radio;
    }

    public double getMasa() {
        return masa;
    }

    public boolean colisionaCon(Bocha otra) {
        double distancia = Math.pow(this.x + otra.x, 2) + Math.pow(this.y + otra.y, 2);
        return distancia == Math.pow(this.radio + otra.radio, 2);
    }
}
