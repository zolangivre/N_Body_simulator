package org.acme;

public class BodyPoint {
    private Long id;
    private String name;
    private double x;
    private double y;
    private double z;
    private double vitesseX;
    private double vitesseY;
    private double accelerationX;
    private double accelerationY;
    private double masse;
    private double angle; // New property for angle

    // Getters and Setters

    public BodyPoint() {}

    public BodyPoint(Long id, String name, double x, double y, double z, double vitesseX, double vitesseY, double accelerationX, double accelerationY, double masse, double angle) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.masse = masse;
        this.angle = angle; // Initialize the new property
    }

    // Getters and setters for all properties

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
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
        this.masse = masse;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}