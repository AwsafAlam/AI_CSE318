import java.util.*;

public class City {
    private double x;
    private double y;
    private City parent;
    private boolean visited;
    private Map<Double , City> neighbour;

    public City(double x, double y) {
        this.x = x;
        this.y = y;
        this.visited = false;
        this.parent = null;
        this.neighbour = new HashMap<>();
    }

    public void addNeighbour(double d, City c){
        neighbour.put(d,c);
    }

    public Map<Double, City> getNeighbour() {
        return neighbour;
    }

    public void setNeighbour(Map<Double, City> neighbour) {
        this.neighbour = neighbour;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return getX() == city.getX() &&
                getY() == city.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }


    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    public City getParent() {
        return parent;
    }

    public void setParent(City parent) {
        this.parent = parent;
    }
}
