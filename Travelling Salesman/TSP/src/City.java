import java.util.HashMap;
import java.util.List;

public class City {
    private int x;
    private int y;
    private boolean visited;
    HashMap<City, Double> neighbours;

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

    public void addNeighbour(City c, double dist){
        neighbours.put(c,dist);
    }

    public HashMap<City, Double> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(HashMap<City, Double> neighbours) {
        this.neighbours = neighbours;
    }

    public City(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighbours = new HashMap<>();
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
