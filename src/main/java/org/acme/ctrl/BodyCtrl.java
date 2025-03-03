package org.acme.ctrl;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.acme.model.BodyPoint;
import org.acme.svc.SimulationService;

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

    @POST
    @Path("/reset")
    public Response resetPoints() {
        points.clear();
        return Response.ok(points).build();
    }

    @POST
    @Path("/deleteHighCoordinates")
    public void deletePointsWithHighCoordinates() {
        points.removeIf(point -> Math.abs(point.getX()) > 20000 || Math.abs(point.getY()) > 20000);
    }
}
