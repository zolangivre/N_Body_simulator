package org.acme.ctrl;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.svc.BodySvc;
import org.acme.model.BodyModel;

import java.util.List;
import java.util.Optional;

@Path("/body")
public class BodyCtrl {

    private final BodySvc simulationService;

    public BodyCtrl(BodySvc simulationService) {
        this.simulationService = simulationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BodyModel addBody(BodyModel body) {
        return simulationService.addBody(body);
    }

    @POST
    @Path("/start")
    public void startSimulation(@QueryParam("dt") double dt, @QueryParam("steps") int steps) {
        simulationService.startSimulation(dt, steps);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BodyModel> getBodies() {
        return simulationService.getBodies();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/title")
    public String title(@QueryParam("p") Optional<Integer> p, @QueryParam("titre") Optional<String> titre) {
        return "hello world";
    }
}