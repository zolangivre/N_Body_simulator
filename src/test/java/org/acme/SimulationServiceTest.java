package org.acme;

import org.acme.model.BodyPoint;
import org.acme.svc.SimulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationServiceTest {

    private SimulationService simulationService;

    @BeforeEach
    public void setUp() {
        simulationService = new SimulationService();
    }

    @Test
    public void testSimulateWithValidPoints() {
        List<BodyPoint> points = new ArrayList<>();
        points.add(new BodyPoint("Point 1", 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 10.0));
        points.add(new BodyPoint("Point 2", 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0));

        List<BodyPoint> result = simulationService.simulate(points, 0.1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertNotEquals(0.0, result.get(0).getX());
        assertNotEquals(10.0, result.get(0).getY());
        assertNotEquals(10.0, result.get(1).getX());
        assertNotEquals(0.0, result.get(1).getY());
    }

    @Test
    public void testSimulateWithNullPoints() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            simulationService.simulate(null, 0.1);
        });
        assertEquals("Points list cannot be null", exception.getMessage());
    }

    @Test
    public void testSimulateWithEmptyPoints() {
        List<BodyPoint> points = new ArrayList<>();
        List<BodyPoint> result = simulationService.simulate(points, 0.1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSimulateWithSinglePoint() {
        List<BodyPoint> points = new ArrayList<>();
        points.add(new BodyPoint("Point 1", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0));

        List<BodyPoint> result = simulationService.simulate(points, 0.1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(0.0, result.get(0).getX());
        assertEquals(0.0, result.get(0).getY());
    }

}