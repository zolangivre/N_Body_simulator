package org.acme;

import jakarta.ws.rs.core.Response;
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
    public void testCreateBodyPoint() {
        int initialSize = bodyCtrl.getAllPoints().size();
        BodyPoint newPoint = bodyCtrl.createBodyPoint();
        List<BodyPoint> points = bodyCtrl.getAllPoints();

        assertNotNull(newPoint);
        assertEquals(initialSize + 1, points.size());
        assertTrue(points.contains(newPoint));
    }
}