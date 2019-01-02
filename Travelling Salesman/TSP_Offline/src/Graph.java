import java.text.DecimalFormat;
import java.util.*;

public class Graph {

    private List<City> cities;
    HashMap<City , Double> tour;

    private DecimalFormat df = new DecimalFormat("#.###");

    public Graph(List<City> cities) {
        this.cities = cities;

    }

    public void printGraph(){

        for (int i = 0; i < cities.size(); i++) {
            System.out.print("("+cities.get(i).getX()+","+cities.get(i).getY()+") : ");

            for (int j = 0; j < cities.size(); j++) {
                // Generate graph for cities
                double dist = calculateDistance(cities.get(i),cities.get(j));
                System.out.print("("+cities.get(j).getX()+","+cities.get(j).getY()+" <-> "+
                        df.format(dist) +")  ");

            }
            System.out.println("");
        }

    }

    private double calculateDistance(City a , City b){
        double dist  = 0;
        dist = Math.sqrt( Math.pow(a.getX() - b.getX() , 2) + Math.pow(a.getY() - b.getY() , 2) );

        return dist;
    }

    public void NearestInsertion(int city){
        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }

        tour = new HashMap<City, Double>();
        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        init.setVisited(true);
        tour.put(init, 0.0);
        n--;
        double distance = 0.0;

        while (n > 0){
            double min = 999999999.0;
            City nextTour = null;

            for (City traversed: tour.keySet()) {

                for (int j = 0; j < cities.size(); j++) {

                    City c = cities.get(j);
                    if(c.isVisited())
                        continue;
                    double neighbour_dist = calculateDistance(c,traversed);
                    if(neighbour_dist < min){// Distance from neighbour
                        min = neighbour_dist;
                        nextTour = c;
                    }
               }

            }
            distance += min;
            nextTour.setVisited(true);
            tour.put(nextTour, Double.valueOf(df.format(distance)));
            n--;

        }

    }


    public void CheapestInsertion(int city){
        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }

        tour = new HashMap<City, Double>();
        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        City c1 = null , c2 = null;
        City nextTour = null;

        init.setVisited(true);
        tour.put(init, 0.0);
        n--;
        double distance = 0.0;

        while (n > 0){
            double min = 999999999.0;
            nextTour = null;
            if(tour.size() < 2){
                for (int j = 0; j < cities.size(); j++) {
                    City c = cities.get(j);
                    if(c.isVisited())
                        continue;
                    double neighbour_dist = calculateDistance(c,init);
                    if(neighbour_dist < min){// Distance from neighbour
                        min = neighbour_dist;
                        nextTour = c;
                    }
                }
                distance += min;
                nextTour.setVisited(true);
                //TODO : Fix graph path
//                init.setChild(nextTour);
//                nextTour.setChild(null);
                tour.put(nextTour, Double.valueOf(df.format(distance)));
                n--;
                continue;
            }
            else {
                for (City ci: tour.keySet()) {
                    for (City cj: tour.keySet()) {
                        for (int k = 0; k < cities.size(); k++) {
                            City ck = cities.get(k);
                            if (ck.isVisited())
                                continue;

                            double edge_Cij = calculateDistance(ci,cj);
                            double edge_Cik = calculateDistance(ci,ck);
                            double edge_Cjk = calculateDistance(ck,cj);

                            if ((edge_Cik + edge_Cjk - edge_Cij) < min ) {
                                min = edge_Cik + edge_Cjk - edge_Cij;
                                nextTour = ck;
                                c1 = ci;
                                c2 = ck;
                            }

                        }

                    }
                }

            }

            distance += min;
            nextTour.setVisited(true);
            //TODO : Fix graph paths for cheapest insertions
//            if(c1.getChild() != null && c1.getChild().equals(c2)){
//                nextTour.setChild(c2);
//                c1.setChild(nextTour);
//            }
//            else{
//                nextTour.setChild(c1);
//                c2.setChild(nextTour);
//            }
            tour.put(nextTour, Double.valueOf(df.format(distance)));
            n--;

        }
//        printPath(nextTour);

    }

    public void NearestNeighbour(int city){
        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }

        tour = new HashMap<City, Double>();
        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        init.setVisited(true);
        tour.put(init, 0.0);
        n--;
        double distance = 0.0;
        City prevTour = init;

        while (n > 0){
            double min = 999999999.0;
            City currentTour = null;

            for (int j = 0; j < cities.size(); j++) {

                City c = cities.get(j);
                if(c.isVisited())
                    continue;

                double neighbour_dist = calculateDistance(c,prevTour);
                if(neighbour_dist < min){// Distance from neighbour
                    min = neighbour_dist;
                    currentTour = c;
                }

            }

            distance += min;
            currentTour.setVisited(true);
            prevTour = currentTour;
            tour.put(currentTour, Double.valueOf(df.format(distance)));
            n--;

        }

    }

    public void Improvement_2_Opt(int city){
        NearestNeighbour(city);

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

    public void printPath(City c){
        while (c != null){
            System.out.print("("+c.getX()+","+c.getY()+") <-> ");
            c = c.getParent();
        }
    }
}
