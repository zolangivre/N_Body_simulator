package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
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
                    "Point " + i,
                    (Math.random() - 0.5) * 100,
                    (Math.random() - 0.5) * 100,
                    (Math.random() - 0.5) * 10,
                    (Math.random() - 0.5) * 10,
                    (Math.random() - 0.5) * 5,
                    (Math.random() - 0.5) * 5,
                    Math.random() * 1000
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
    public BodyPoint createBodyPoint() {
        BodyPoint point = new BodyPoint(
                "Point " + (points.size() + 1),
                (Math.random() - 0.5) * 100,
                (Math.random() - 0.5) * 100,
                (Math.random() - 0.5) * 10,
                (Math.random() - 0.5) * 10,
                (Math.random() - 0.5) * 5,
                (Math.random() - 0.5) * 5,
                Math.random() * 1000
        );
        points.add(point);
        return point;
    }

    @DELETE
    @Path("/reset")
    public void resetPoints() {
        points.clear();
    }

    @POST
    @Path("/deleteHighCoordinates")
    public void deletePointsWithHighCoordinates(@QueryParam("threshold") double threshold) {
        points.removeIf(point -> Math.abs(point.getX()) > threshold || Math.abs(point.getY()) > threshold);
    }
}
