package org.acme.ui;

import org.acme.model.BodyModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationPanel extends JPanel {
    private List<BodyModel> bodies;

    public SimulationPanel(List<BodyModel> bodies) {
        this.bodies = bodies;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (BodyModel body : bodies) {
            int x = body.getX();
            int y = body.getY();
            g2d.fillOval(x, y, 5, 5);
        }
    }

    public void setBodies(List<BodyModel> bodies) {
        this.bodies = bodies;
        repaint();
    }
}