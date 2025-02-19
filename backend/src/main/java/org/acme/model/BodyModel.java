package org.acme.model;

public class BodyModel {
    Double vx;
    Double vy;
    Double acceleration;
    Double masse;
    Double angle;
    double x;
    double y;



    public Double getMasse() {
        return masse;
    }

    public Integer getX() {
        return (int) x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return (int) y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public BodyModel(Double vx,Double vy, Double acceleration, Double masse, Double angle, Integer x, Integer y) {
        this.vx = vx;
        this.vy = vy;
        this.acceleration = acceleration;
        this.masse = masse;
        this.angle = angle;
        this.x = x;
        this.y = y;
    }

    public void updatePosition(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    public void updateVelocity(double ax, double ay, double dt) {
        vx += ax * dt;
        vy += ay * dt;
    }

}
