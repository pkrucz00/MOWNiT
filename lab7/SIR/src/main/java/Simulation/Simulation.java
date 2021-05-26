package Simulation;

import GUI.XYLineChart;
import World.Cell;
import World.MyMap;

import java.util.*;


public class Simulation {
    private final MyMap worldMap;
    private final double mortalityRate;

    private final StatisticsObserver observer;

    public Simulation(int mapWidth, int mapHeight, double mortalityRate, int initialNoInfectedCells){
        this.worldMap = new MyMap(mapWidth, mapHeight);
        this.mortalityRate = mortalityRate;
        for (int i = 0; i < initialNoInfectedCells; i++){
            worldMap.getRandomCell().getIll();
        }
        this.observer = new StatisticsObserver();
    }

    public void step() {
        Set<Cell> cellSet = this.worldMap.getInfectedCells();
        Iterator<Cell> it = cellSet.iterator();
        while (it.hasNext()){
            Cell cell = it.next();
            double survivableFactor = Math.random();
            if (survivableFactor < mortalityRate){
                cell.die();
            } else {
                Cell randNeighbour = this.worldMap.getRandomNeighbour(cell);
                randNeighbour.getIll();
            }
        }
        observer.observe(this.worldMap);
    }

    public XYLineChart generateChart(String appTitle, String chartTitle, int dimX, int dimY){
        return new XYLineChart(appTitle, chartTitle, this.observer, dimX, dimY);
    }

    public MyMap getWorldMap() {
        return worldMap;
    }

    public StatisticsObserver getObserver() {
        return observer;
    }
}
