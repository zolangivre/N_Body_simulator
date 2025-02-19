package org.acme.svc;

import org.acme.model.BodyModel;
import java.util.ArrayList;
import java.util.List;

public class BodySvc {
    private List<BodyModel> bodies = new ArrayList<>();
    private static final double G = 6.67430e-11; // Gravitational constant

    public BodyModel addBody(BodyModel body) {
        bodies.add(body);
        return body;
    }

    public void startSimulation(double dt, int steps) {
        for (int i = 0; i < steps; i++) {
            update(dt);
        }
    }

    private void update(double dt) {
        for (BodyModel body : bodies) {
            double ax = 0;
            double ay = 0;
            for (BodyModel other : bodies) {
                if (body != other) {
                    double[] force = computeGravitationalForce(body, other);
                    ax += force[0] / body.getMasse();
                    ay += force[1] / body.getMasse();
                }
            }
            body.updateVelocity(ax, ay, dt);
        }

        for (BodyModel body : bodies) {
            body.updatePosition(dt);
        }
    }

    private double[] computeGravitationalForce(BodyModel b1, BodyModel b2) {
        double dx = b2.getX() - b1.getX();
        double dy = b2.getY() - b1.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double force = G * b1.getMasse() * b2.getMasse() / (distance * distance);
        return new double[]{force * dx / distance, force * dy / distance};
    }

    public List<BodyModel> getBodies() {
        return bodies;
    }

    public void setBodies(List<BodyModel> bodies) {
        this.bodies = bodies;
    }
}