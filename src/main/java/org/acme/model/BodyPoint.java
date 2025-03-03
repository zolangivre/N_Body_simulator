package org.acme.model;

public class BodyPoint {
    private String name;
    private double x;
    private double y;
    private double vitesseX;
    private double vitesseY;
    private double accelerationX;
    private double accelerationY;
    private double masse;

    public BodyPoint(String name, double x, double y, double vitesseX, double vitesseY, double accelerationX, double accelerationY, double masse) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.masse = masse;
    }

    // Getters and setters for all properties

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVitesseX() {
        return vitesseX;
    }

    public void setVitesseX(double vitesseX) {
        this.vitesseX = vitesseX;
    }

    public double getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(double vitesseY) {
        this.vitesseY = vitesseY;
    }

    public double getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(double accelerationX) {
        this.accelerationX = accelerationX;
    }

    public double getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(double accelerationY) {
        this.accelerationY = accelerationY;
    }

    public double getMasse() {
        return masse;
    }

    public void setMasse(double masse) {
        if (masse < 0) {
            throw new IllegalArgumentException("La masse ne peut pas être négative");
        }
        this.masse = masse;
    }
}