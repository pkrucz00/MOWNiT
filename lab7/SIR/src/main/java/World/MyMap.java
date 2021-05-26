package World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MyMap {
    private final int width;
    private final int height;

    private Cell[][] cells;
    private Set<Cell> infectedCells = new CopyOnWriteArraySet<>();

    public MyMap(int width, int height){
        this.width = width;
        this.height = height;

        this.cells = new Cell[width][height];
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                cells[x][y] = new Cell(x, y, this);
            }
        }
    }

    public void addToIll(Cell cell){
        infectedCells.add(cell);
    }

    public void removeFromIll(Cell cell){
        infectedCells.remove(cell);
    }

    public Set<Cell> getInfectedCells() {
        return infectedCells;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getRandomCell(){
        Random random = new Random();
        int randX = random.nextInt(this.width);
        int randY = random.nextInt(this.height);
        return this.getCellAtPosition(randX, randY);
    }


    public Cell getRandomNeighbour(Cell cell){
        Random random = new Random();
        int xOffset, yOffset, x, y;
        Cell resultCell;
        do {
            xOffset = random.nextInt(3) - 1;
            yOffset = random.nextInt(3) - 1;
            x = Math.floorMod(cell.getX() + xOffset, width);
            y = Math.floorMod(cell.getY() + yOffset, height);
            resultCell = this.getCellAtPosition(x, y);
        } while (xOffset == 0 && yOffset == 0);

        return resultCell;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getCellAtPosition(int x, int y){
        return cells[x][y];
    }

    public int getNoCellsWithGivenState(CellState state){
        return Arrays.stream(this.cells).flatMap(Arrays::stream)
                .filter(e -> e.getState() == state)
                .mapToInt(e -> 1)
                .sum();
    }

    public int getNoSusceptibleCells(){
        return getNoCellsWithGivenState(CellState.SUSCEPTIBLE);
    }

    public int getNoInfectedCells(){
        return getNoCellsWithGivenState(CellState.INFECTED);
    }

    public int getNoRemovedCells(){
        return getNoCellsWithGivenState(CellState.REMOVED);
    }
}
