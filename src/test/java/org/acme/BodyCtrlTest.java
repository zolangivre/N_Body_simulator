package org.acme;

import org.acme.ctrl.BodyCtrl;
import org.acme.model.BodyPoint;
import org.acme.svc.SimulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BodyCtrlTest {

    @Mock
    private SimulationService simulationService;

    @InjectMocks
    private BodyCtrl bodyCtrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bodyCtrl = new BodyCtrl();
        simulationService = mock(SimulationService.class);

    }

    @Test
    public void testGetAllPoints() {
        List<BodyPoint> points = bodyCtrl.getAllPoints();
        assertNotNull(points);
        assertEquals(5, points.size());
    }

    @Test
    public void testGetallPointsEmpty() {
        List<BodyPoint> points = bodyCtrl.getAllPoints();
        assertNotNull(points);
        bodyCtrl.resetPoints();
        assertEquals(0, points.size());
    }

    @Test
    public void testCreateBodyPoint() {
        int initialSize = bodyCtrl.getAllPoints().size();
        BodyPoint newPoint = bodyCtrl.createBodyPoint();
        List<BodyPoint> points = bodyCtrl.getAllPoints();

        assertNotNull(newPoint);
        assertEquals(initialSize + 1, points.size());
        assertTrue(points.contains(newPoint));
    }

    @Test
    public void testResetPoints() {
        // Ensure there are initial points
        List<BodyPoint> initialPoints = bodyCtrl.getAllPoints();
        assertNotNull(initialPoints);
        assertFalse(initialPoints.isEmpty());

        // Call the resetPoints method
        bodyCtrl.resetPoints();

        // Verify that points are cleared
        List<BodyPoint> pointsAfterReset = bodyCtrl.getAllPoints();
        assertNotNull(pointsAfterReset);
        assertTrue(pointsAfterReset.isEmpty());
    }

    @Test
    public void testDeletePointsWithHighCoordinatesTrue() {
        // Ensure there are initial points
        List<BodyPoint> initialPoints = bodyCtrl.getAllPoints();
        assertNotNull(initialPoints);
        assertFalse(initialPoints.isEmpty());

        // Add a point with high coordinates
        BodyPoint highPoint = new BodyPoint("High Point", 25000.0, 25000.0, 0.0, 0.0, 0.0, 0.0, 100.0);
        initialPoints.add(highPoint);

        // Call the deletePointsWithHighCoordinates method
        bodyCtrl.deletePointsWithHighCoordinates();

        // Verify that points with high coordinates are removed
        List<BodyPoint> pointsAfterDeletion = bodyCtrl.getAllPoints();
        assertNotNull(pointsAfterDeletion);
        assertFalse(pointsAfterDeletion.contains(highPoint));
    }

    @Test
    public void testDeletePointsWithHighCoordinatesFalse() {
        // Ensure there are initial points
        List<BodyPoint> initialPoints = bodyCtrl.getAllPoints();
        assertNotNull(initialPoints);
        assertFalse(initialPoints.isEmpty());

        // Add a point with high coordinates
        BodyPoint lowPoint = new BodyPoint("low Point", 25.0, 25.0, 0.0, 0.0, 0.0, 0.0, 100.0);
        initialPoints.add(lowPoint);

        // Call the deletePointsWithHighCoordinates method
        bodyCtrl.deletePointsWithHighCoordinates();

        // Verify that points with high coordinates are removed
        List<BodyPoint> pointsAfterDeletion = bodyCtrl.getAllPoints();
        assertTrue(pointsAfterDeletion.contains(lowPoint));
    }
}