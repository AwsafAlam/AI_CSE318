import java.util.List;

public class Graph {

    private List<City> cities;

    public Graph(List<City> cities) {
        this.cities = cities;
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                
            }
        }


    }

    private double calculateDistance(City a , City b){
        double dist  = 0;
        dist = Math.sqrt( Math.pow(a.getX() - b.getX() , 2) + Math.pow(a.getY() - b.getY() , 2) );

        return dist;
    }
}
