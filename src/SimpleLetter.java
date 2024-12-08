public class SimpleLetter {
    int xCoordinate;
    int yCoordinate;
    char letter;
    boolean active;
    public SimpleLetter(boolean active, int x, int y, char letter){
        this.active = active;
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.letter = letter;
    }
}
