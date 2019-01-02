import java.util.Objects;

public class City {
    private double x;
    private double y;
    private City parent;
    private boolean visited;

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

    public City(double x, double y) {
        this.x = x;
        this.y = y;
        this.visited = false;
        this.parent = null;
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
