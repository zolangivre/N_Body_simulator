package org.acme;

import org.acme.model.BodyPoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BodyPointTest {

    @Test
    public void testPositiveValues() {
        BodyPoint point = new BodyPoint("Point 1", 10.0, 20.0, 5.0, 5.0, 1.0, 1.0, 100.0);
        assertEquals(10.0, point.getX());
        assertEquals(20.0, point.getY());
        assertEquals(5.0, point.getVitesseX());
        assertEquals(5.0, point.getVitesseY());
        assertEquals(1.0, point.getAccelerationX());
        assertEquals(1.0, point.getAccelerationY());
        assertEquals(100.0, point.getMasse());
    }

    @Test
    public void testNegativeValues() {
        BodyPoint point = new BodyPoint("Point 2", -10.0, -20.0, -5.0, -5.0, -1.0, -1.0, 100.0);
        assertEquals(-10.0, point.getX());
        assertEquals(-20.0, point.getY());
        assertEquals(-5.0, point.getVitesseX());
        assertEquals(-5.0, point.getVitesseY());
        assertEquals(-1.0, point.getAccelerationX());
        assertEquals(-1.0, point.getAccelerationY());
        assertEquals(100.0, point.getMasse());
    }

    @Test
    public void testZeroValues() {
        BodyPoint point = new BodyPoint("Point 3", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);
        assertEquals(0.0, point.getX());
        assertEquals(0.0, point.getY());
        assertEquals(0.0, point.getVitesseX());
        assertEquals(0.0, point.getVitesseY());
        assertEquals(0.0, point.getAccelerationX());
        assertEquals(0.0, point.getAccelerationY());
        assertEquals(100.0, point.getMasse());
    }

    @Test
    public void testSetMasseWithPositiveValue() {
        BodyPoint point = new BodyPoint("Point 4", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);
        point.setMasse(200.0);
        assertEquals(200.0, point.getMasse());
    }

    @Test
    public void testSetMasseWithZeroValue() {
        BodyPoint point = new BodyPoint("Point 5", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);
        point.setMasse(0.0);
        assertEquals(0.0, point.getMasse());
    }

    @Test
    public void testSetMasseWithNegativeValue() {
        BodyPoint point = new BodyPoint("Point 6", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            point.setMasse(-100.0);
        });
        assertEquals("La masse ne peut pas être négative", exception.getMessage());
    }
}