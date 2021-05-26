package World;

public enum CellState {
    SUSCEPTIBLE,
    INFECTED,
    REMOVED;

    public String toString(){
        return switch (this) {
            case SUSCEPTIBLE -> "Sus";
            case INFECTED -> "Inf";
            case REMOVED -> "Rem";
        };
    }
}
