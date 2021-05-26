package Simulation;

public class Parser {
    public final int mapWidth;
    public final int mapHeight;
    public final double mortalityRate;
    public final int initialNoInfectedCells;

    public Parser(String[] args){
        mapWidth = Integer.parseInt(args[0]);
        mapHeight = Integer.parseInt(args[1]);
        mortalityRate = Double.parseDouble(args[2]);
        initialNoInfectedCells = Integer.parseInt(args[3]);
    }
}
