import java.util.List;

public class City {
    private int x;
    private int y;
    List<City> neighbours;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<City> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<City> neighbours) {
        this.neighbours = neighbours;
    }

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
