package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class SimulationService {

    private final Random random = new Random();
    private static final double MOVEMENT_VARIABILITY = 0.1; // Variabilité aléatoire
    private static final double G = 0.1; // Constante gravitationnelle simulée

    public List<BodyPoint> simulate(List<BodyPoint> points, double deltaTime) {
        if (points == null) {
            throw new IllegalArgumentException("Points list cannot be null");
        }

        int n = points.size();

        // Calcul des forces d'interaction pour chaque point avec tous les autres
        for (int i = 0; i < n; i++) {
            BodyPoint p1 = points.get(i);

            double totalAx = 0;
            double totalAy = 0;

            for (int j = 0; j < n; j++) {
                if (i == j) continue; // Ne pas calculer l'interaction avec soi-même

                BodyPoint p2 = points.get(j);

                // Calcul de la distance entre les deux points
                double dx = p2.getX() - p1.getX();
                double dy = p2.getY() - p1.getY();
                double distanceSquared = dx * dx + dy * dy;
                double distance = Math.sqrt(distanceSquared);

                // Éviter les divisions par zéro
                if (distance < 1e-5) continue;

                // Calcul de la force gravitationnelle
                double force = G * (p1.getMasse() * p2.getMasse()) / distanceSquared;

                // Calcul des accélérations
                double ax = force * (dx / distance) / p1.getMasse();
                double ay = force * (dy / distance) / p1.getMasse();

                totalAx += ax;
                totalAy += ay;
            }

//            // Ajout d'une variation aléatoire pour éviter un mouvement trop uniforme
//            totalAx += (random.nextDouble() - 0.5) * MOVEMENT_VARIABILITY;
//            totalAy += (random.nextDouble() - 0.5) * MOVEMENT_VARIABILITY;

            // Mise à jour des accélérations
            p1.setAccelerationX(totalAx);
            p1.setAccelerationY(totalAy);
        }

        // Mise à jour des vitesses et positions
        for (BodyPoint point : points) {
            point.setVitesseX(point.getVitesseX() + point.getAccelerationX() * deltaTime);
            point.setVitesseY(point.getVitesseY() + point.getAccelerationY() * deltaTime);

            point.setX(point.getX() + point.getVitesseX() * deltaTime);
            point.setY(point.getY() + point.getVitesseY() * deltaTime);
        }

        return points;
    }
}
