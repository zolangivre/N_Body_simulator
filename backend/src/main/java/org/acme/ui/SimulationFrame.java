package org.acme.ui;

import org.acme.model.BodyModel;
import org.acme.svc.BodySvc;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private SimulationPanel simulationPanel;
    private BodySvc bodySvc;

    public SimulationFrame(BodySvc bodySvc) {
        this.bodySvc = bodySvc;
        this.simulationPanel = new SimulationPanel(bodySvc.getBodies());
        setTitle("Body Simulation");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(simulationPanel, BorderLayout.CENTER);
        setVisible(true);
        startSimulation();
    }

    private void startSimulation() {
        new Timer(15, e -> {
            bodySvc.startSimulation(0.1, 1);
            simulationPanel.setBodies(bodySvc.getBodies());
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BodySvc bodySvc = new BodySvc();
            Random random = new Random();

            for (int i = 0; i < 1000; i++) {
                double vx = random.nextDouble() * 50;
                double vy = random.nextDouble() * 50;
                double acceleration = random.nextDouble();
                double masse = random.nextDouble() * 1000;
                double angle = random.nextDouble() * 360;
                int x = random.nextInt(500);
                int y = random.nextInt(400);
                bodySvc.addBody(new BodyModel(vx, vy, acceleration, masse, angle, x, y));
            }

            new SimulationFrame(bodySvc);
        });
    }
}