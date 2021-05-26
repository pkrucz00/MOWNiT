package GUI;

import Simulation.Parser;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private final SimulationPanel simulationPanel;

    public SimulationFrame(Parser parser){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        simulationPanel = new SimulationPanel(parser);
        this.add(simulationPanel, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Automat komórkowy");
    }
}
