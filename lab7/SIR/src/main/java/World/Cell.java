package World;

public class Cell {
    private CellState state;
    private final int x;
    private final int y;
    private final MyMap map;

    public Cell(int posX, int posY, CellState initialState, MyMap map){
        this.x = posX;
        this.y = posY;
        this.state = initialState;
        this.map = map;
    }

    public Cell(int posX, int posY, MyMap map){
        this(posX, posY, CellState.SUSCEPTIBLE, map);
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public void getIll(){
        if (this.getState() == CellState.SUSCEPTIBLE) {
            this.setState(CellState.INFECTED);
            this.map.addToIll(this);
        }
    }

    public void die(){
        this.setState(CellState.REMOVED);
        this.map.removeFromIll(this);
    }

    public CellState getState(){
        return this.state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y &&
                state == cell.state;
    }


    @Override
    public String toString() {
        return "Cell{" +
                "state=" + state +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
