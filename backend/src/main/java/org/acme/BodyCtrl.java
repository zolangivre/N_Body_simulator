package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Path("/body")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BodyCtrl {
    @Inject
    SimulationService simulationService;

    private List<BodyPoint> points = new ArrayList<>();

    public BodyCtrl() {
        for (int i = 1; i <= 5; i++) {
            points.add(new BodyPoint(
                    (long) i,
                    "Point " + i,
                    (Math.random() - 0.5) * 100,
                    (Math.random() - 0.5) * 100,
                    (Math.random() - 0.5) * 10,
                    (Math.random() - 0.5) * 10,
                    (Math.random() - 0.5) * 5,
                    (Math.random() - 0.5) * 5,
                    (Math.random() - 0.5) * 10,
                    Math.random() * 360 // Random angle between 0 and 360 degrees
            ));
        }
    }

    @GET
    public List<BodyPoint> getAllPoints() {
        return points;
    }

    @POST
    @Path("/simulate")
    public List<BodyPoint> simulateMovement() {
        double deltaTime = 0.1; // Adjust as needed
        return simulationService.simulate(points, deltaTime);
    }

    @POST
    @Path("/move")
    public List<BodyPoint> movePoint(BodyPoint point) {
        for (BodyPoint p : points) {
            if (p.getId().equals(point.getId())) {
                p.setX(point.getX());
                p.setY(point.getY());
                p.setVitesseX(point.getVitesseX());
                p.setVitesseY(point.getVitesseY());
                p.setAccelerationX(point.getAccelerationX());
                p.setAccelerationY(point.getAccelerationY());
                p.setMasse(point.getMasse());
            }
        }
        return points;
    }

    @POST
    public BodyPoint createBodyPoint() {
        BodyPoint point = new BodyPoint(
                (long) (points.size() + 1),
                "Point " + (points.size() + 1),
                (Math.random() - 0.5) * 100,
                (Math.random() - 0.5) * 100,
                (Math.random() - 0.5) * 10,
                (Math.random() - 0.5) * 10,
                (Math.random() - 0.5) * 5,
                (Math.random() - 0.5) * 5,
                (Math.random() - 0.5) * 10,
                Math.random() * 360 // Random angle between 0 and 360 degrees
        );
        points.add(point);
        return point;
    }
}
