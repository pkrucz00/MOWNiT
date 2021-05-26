package Simulation;

import World.MyMap;

import java.util.ArrayList;


public class StatisticsObserver {
    private final ArrayList<Integer> noInfectedTable = new ArrayList<>();
    private final ArrayList<Integer> noSusceptibleTable = new ArrayList<>();
    private final ArrayList<Integer> noRemovedTable = new ArrayList<>();
    private int noIterations;

    public void observe(MyMap world){
        noIterations++;
        noInfectedTable.add(world.getNoInfectedCells());
        noSusceptibleTable.add(world.getNoSusceptibleCells());
        noRemovedTable.add(world.getNoRemovedCells());
    }

    public void printStatistics(){
        System.out.println("Iterations: " + noIterations);
        System.out.println("S: " + noSusceptibleTable.get(noIterations-1));
        System.out.println("I: " + noInfectedTable.get(noIterations-1));
        System.out.println("R: " + noRemovedTable.get(noIterations-1));
        System.out.println();
    }

    public ArrayList<Integer> getNoInfectedTable() {
        return noInfectedTable;
    }

    public ArrayList<Integer> getNoRemovedTable() {
        return noRemovedTable;
    }

    public ArrayList<Integer> getNoSusceptibleTable() {
        return noSusceptibleTable;
    }

    public int getNoIterations() {
        return noIterations;
    }

    //    public void saveToCSV(String path) throws IOException {
//        CSVWriter writer = new CSVWriter(new FileWriter(path));
//        writer.writeAll(noSusceptibleTable.toArray());
//    }
//
//    private String[] ListToStr(List<Integer> list){
//        return list.stream().map(Integer::toString).toArray();
//    }
}
