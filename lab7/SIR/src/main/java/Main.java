import GUI.SimulationFrame;
import Simulation.Simulation;
import World.MyMap;
import Simulation.Parser;


public class Main {
    public static void main(String args[]) throws InterruptedException {
        Parser parser = new Parser(args);
//        Simulation simulation = new Simulation(parser.mapWidth, parser.mapHeight,
//                parser.mortalityRate, parser.initialNoInfectedCells);
//        simulation.run();
        new SimulationFrame(parser);

    }
}
