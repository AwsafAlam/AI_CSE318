import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.CheckedInputStream;

public class Graph {

    private List<City> cities;
    HashMap<City , Double> tour;

//    private HashMap<City, HashMap<City, Double>> g;
    private DecimalFormat df = new DecimalFormat("#.###");

    public Graph(List<City> cities) {
        this.cities = cities;
//        this.g = new HashMap<>();
        for (int i = 0; i < cities.size(); i++) {
//            HashMap<City, Double> neigh = new HashMap<>();
            for (int j = i+1; j < cities.size(); j++) {
                // Generate graph for cities
                double dist = calculateDistance(cities.get(i),cities.get(j));
                cities.get(i).addNeighbour(cities.get(j) , Double.valueOf(df.format(dist)));
                cities.get(j).addNeighbour(cities.get(i) , Double.valueOf(df.format(dist)));
//                neigh.put(cities.get(j) , Double.valueOf(df.format(dist)));

            }
//            g.put(cities.get(i),neigh);

        }


    }

    public void printGraph(){
        for (int i = 0; i < cities.size(); i++) {
            System.out.print("("+cities.get(i).getX()+","+cities.get(i).getY()+") : ");

            for (City c: cities.get(i).getNeighbours().keySet()) {
                System.out.print("("+c.getX()+","+c.getY()+" <-> "+
                        cities.get(i).getNeighbours().get(c)+")  ");
            }
            System.out.println("");
        }
    }

    private double calculateDistance(City a , City b){
        double dist  = 0;
        dist = Math.sqrt( Math.pow(a.getX() - b.getX() , 2) + Math.pow(a.getY() - b.getY() , 2) );

        return dist;
    }

    public void nearestNeighbour(int city){
        tour = new HashMap<City, Double>();
        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        tour.put(init, 0.0);
        n--;
        double distance = 0.0;

        while (n > 0){
            double min = 999999999.0;
            City nextTour = null;

            for (City traversed: tour.keySet()) {

                for (City c: traversed.getNeighbours().keySet()) {
                    if(c.isVisited())
                        continue;
                    double neighbour_dist = c.getNeighbours().get(traversed);
                    if(neighbour_dist < min){// Distance from neighbour
                        min = neighbour_dist;
                        nextTour = c;
                    }

                }
            }
            distance += min;
            nextTour.setVisited(true);
            tour.put(nextTour, distance);
            n--;

        }

    }


    private static Map<City, Double> sortByComparator(Map<City, Double> unsortMap, final boolean order)
    {

        List<Map.Entry<City, Double>> list = new LinkedList<Map.Entry<City, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<City, Double>>()
        {
            public int compare(Map.Entry<City, Double> o1,
                               Map.Entry<City, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<City, Double> sortedMap = new LinkedHashMap<City, Double>();
        for (Map.Entry<City, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    public void printPath() {
        Map<City, Double> sortedMapAsc = sortByComparator(tour, true);

        System.out.println("Printing Path of Travelling Salesman");
        for (City c: sortedMapAsc.keySet()) {
            System.out.println("("+c.getX()+","+c.getY()+" <-> "+
                    Double.valueOf(df.format(sortedMapAsc.get(c)))+")  ");
        }
    }
}
