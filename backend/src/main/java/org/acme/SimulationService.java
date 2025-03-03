package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class SimulationService {

    private final Random random = new Random();
    private static final double MOVEMENT_VARIABILITY = 0.1; // La variabilité du mouvement aléatoire

    public List<BodyPoint> simulate(List<BodyPoint> points, double deltaTime) {
        if (points == null) {
            throw new IllegalArgumentException("Points list cannot be null");
        }

        for (BodyPoint point : points) {
            // Application d'un mouvement aléatoire sur les accélérations
            point.setAccelerationX(point.getAccelerationX() + (random.nextDouble() - 0.5) * MOVEMENT_VARIABILITY);
            point.setAccelerationY(point.getAccelerationY() + (random.nextDouble() - 0.5) * MOVEMENT_VARIABILITY);

            // Mise à jour de l'angle
            point.setAngle(point.getAngle() + (random.nextDouble() - 0.5) * MOVEMENT_VARIABILITY);

            // Calcul des nouvelles vitesses en fonction de l'angle
            double angleRad = Math.toRadians(point.getAngle());
            point.setVitesseX(point.getVitesseX() + Math.cos(angleRad) * point.getAccelerationX() * deltaTime);
            point.setVitesseY(point.getVitesseY() + Math.sin(angleRad) * point.getAccelerationY() * deltaTime);

            // Mise à jour des positions en fonction des vitesses
            point.setX(point.getX() + point.getVitesseX() * deltaTime);
            point.setY(point.getY() + point.getVitesseY() * deltaTime);
        }

        return points;
    }
}