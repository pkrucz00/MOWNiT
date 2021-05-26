package GUI;

import Simulation.Simulation;
import Simulation.Parser;
import World.MyMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationPanel extends JPanel implements ActionListener {
    Timer timer;
    private int SIM_HEIGHT = 600;    //we need to change it once - otherwise white line on the left would appear
    private final int SIM_WIDTH;
    private final int UNIT_SIZE;
    private final int DELAY = 100;

    private final MyMap worldMap;
    private final Simulation simulation;

    private boolean running = false;

    public SimulationPanel(Parser parser){
        UNIT_SIZE = SIM_HEIGHT / parser.mapHeight;       //divided by height of the map
        SIM_WIDTH = UNIT_SIZE * parser.mapWidth;       //multiplied by height of map
        SIM_HEIGHT = UNIT_SIZE * parser.mapHeight;        //divided by width

        this.simulation = new Simulation(parser.mapWidth, parser.mapHeight,
                parser.mortalityRate, parser.initialNoInfectedCells);
        this.worldMap = simulation.getWorldMap();

        this.setPreferredSize(new Dimension(SIM_WIDTH, SIM_HEIGHT));
        this.setBackground(Color.cyan);

        startSimulation();

    }

    public void startSimulation() {
        timer = new Timer(DELAY, this);
        timer.start();
        running = true;
    }

    public void endSimulation() {
        running = false;
        repaint();
        timer.stop();
        showStatistics();
    }

    private void showStatistics(){
        XYLineChart chart = simulation.generateChart("End statistics", "End statistics",
                SIM_WIDTH, SIM_HEIGHT);
        chart.pack();
        chart.setLocationRelativeTo(null);
        chart.setVisible(true);
    }

    public void paint(Graphics g) {
        if (running) {
            for (int y = 0; y < worldMap.getHeight(); y++){
                for (int x = 0; x < worldMap.getWidth(); x++){
                    switch (worldMap.getCellAtPosition(x, y).getState()){
                        case SUSCEPTIBLE -> g.setColor(Color.BLUE);
                        case INFECTED -> g.setColor(Color.ORANGE);
                        case REMOVED -> g.setColor(Color.RED);
                    }
                    g.fillRect(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
            }
        } else {
            g.setColor(Color.CYAN);
            g.fillRect(0, 0,
                    worldMap.getWidth()*UNIT_SIZE,
                    worldMap.getHeight()*UNIT_SIZE);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Helvetica", Font.ITALIC, 60));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("End of simulation",
                    (SIM_WIDTH - metrics2.stringWidth("All animals died")) / 2,
                    SIM_HEIGHT / 2);
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            simulation.step();
        }
        if (worldMap.getNoSusceptibleCells() == 0 ||
                worldMap.getNoInfectedCells() == 0){
            endSimulation();
        }
        repaint();
    }
}
