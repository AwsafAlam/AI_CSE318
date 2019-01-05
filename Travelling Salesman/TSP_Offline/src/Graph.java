import java.text.DecimalFormat;
import java.util.*;

public class Graph {

    private List<City> cities;
//    HashMap<City , Double> tour;
    List<City> tour;
    List<City> new_tour;

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

//        tour = new HashMap<City, Double>();
        tour = new ArrayList<>();
        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        init.setVisited(true);
        tour.add(init);
//        tour.put(init, 0.0);
        n--;
        double distance = 0.0;

        while (n > 0){
            double min = 999999999.0;
            City nextTour = null;

            for (City traversed: tour) {

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
//            tour.put(nextTour, Double.valueOf(df.format(distance)));
            tour.add(nextTour);
            n--;

        }

    }


    public void CheapestInsertion(int city){
        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }

//        tour = new HashMap<City, Double>();
        tour = new ArrayList<>();

        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        City c1 = null , c2 = null;
        City nextTour = null;

        init.setVisited(true);
//        tour.put(init, 0.0);
        tour.add(init);
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
//                tour.put(nextTour, Double.valueOf(df.format(distance)));
                tour.add(nextTour);
                n--;
                continue;
            }
            else {
                for (City ci: tour) {
                    for (City cj: tour) {
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
//            tour.put(nextTour, Double.valueOf(df.format(distance)));
            tour.add(nextTour);
            n--;

        }
//        printPath(nextTour);

    }

    public void NearestNeighbour(int city){
        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }

//        tour = new HashMap<City, Double>();
        tour = new ArrayList<>();
        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        init.setVisited(true);
//        tour.put(init, 0.0);
        tour.add(init);
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
//            tour.put(currentTour, Double.valueOf(df.format(distance)));
            tour.add(currentTour);
            n--;

        }

    }

//    Input: A complete graph with distances defined on the edges and a route and its distance
//    Output: A 2-opt optimal route
//      1: repeat
//      2: for i ∈ Nodes eligible to be swapped do
//      3: for j ∈ Nodes eligible to be swapped such that j > i do
//      4: Apply 2-opt swap to i and j: create the new route as follows:
//      5: Take route upto i and add in order
//      6: Take route from j to i (both including) and add in reverse order
//      7: Take the route after k and add in order
//      8: Calculate new distance
//      9: if new distance < distance then
//      10: Update route to include new ordering
//      11: end if
//      12: end for
//      13: end for
//            14: until no improvement is made
    public void Two_Opt(int city,int cut_off){
        NearestNeighbour(city);

        // Get tour size
        int size = tour.size();
        new_tour = new ArrayList<>();

        for (int i=0;i<size;i++)
        {

            new_tour.add(tour.get(i));
        }

        // repeat until no improvement is made
        int improve = 0;
        int iteration = 0;

        while ( improve < cut_off )
        {
            double best_distance = getTourDistance(tour);

            for ( int i = 1; i < size - 1; i++ )
            {
                for ( int k = i + 1; k < size; k++)
                {
                    TwoOptSwap( i, k );
                    iteration++;
                    double new_distance = getTourDistance(new_tour);

                    if ( new_distance < best_distance )
                    {
                        // Improvement found so reset
                        improve = 0;

                        for (int j=0;j<size;j++)
                        {
                            tour.add(j,new_tour.get(j));
                        }

                        best_distance = new_distance;

                        // Update the display
                        System.out.println("Improment found: "+Double.toString(best_distance)+" it: "+ Integer.toString(iteration));
                    }
                    else {
                        System.out.println("No Improment : "+Double.toString(best_distance)+" - "+Double.toString(new_distance)+" it: "+ Integer.toString(iteration));
                    }
                }
            }

            improve ++;

        }
    }


    private void TwoOptSwap( int i, int k )
    {
        int size = tour.size();

        // 1. take route[0] to route[i-1] and add them in order to new_route
        for ( int c = 0; c <= i - 1; ++c )
        {
            new_tour.add(c,tour.get(c));
        }

        // 2. take route[i] to route[k] and add them in reverse order to new_route
        int dec = 0;
        for ( int c = i; c <= k; ++c )
        {
            new_tour.add(c, tour.get(k-dec));
            dec++;
        }

        // 3. take route[k+1] to end and add them in order to new_route
        for ( int c = k + 1; c < size; ++c )
        {
            new_tour.add(c,tour.get(c));
        }
    }

    public void Three_Opt(int city, int cut_off){

        NearestNeighbour(city);
        // Get tour size
        int size = tour.size();
        new_tour = new ArrayList<>();

        for (int i=0;i<size;i++)
        {

            new_tour.add(tour.get(i));
        }

        // repeat until no improvement is made
        int improve = 0;
        int iteration = 0;

        while ( improve < cut_off )
        {
            double best_distance = getTourDistance(tour);

            for (int i = 1; i < tour.size()-3; ++i)
            {
                for (int j = i+1; j < tour.size()-2; ++j)
                {
                    for (int k = j+1; k < tour.size()-1; ++k)
                    {
                        // Perform the 3 way swap and test the length
                        //swapIndexes(i, k);
                        //swapIndexes(j, k);
                        TwoOptSwap(i,k);
                        TwoOptSwap(j,k);
                        iteration++;

                        double new_distance = getTourDistance(new_tour);

                        if (new_distance < best_distance){
                            // Improvement found so reset
                            improve = 0;

                            for (int p=0;p<size;p++)
                            {
                                tour.add(p,new_tour.get(p));
                            }

                            best_distance = new_distance;

                            // Update the display
                            System.out.println("Improment found: "+df.format(best_distance)+" it: "+ Integer.toString(iteration));
                        }
                        else {
                            System.out.println("No Improment : "+df.format(best_distance)+" - "+df.format(new_distance)+" it: "+ Integer.toString(iteration));
                        }

                    }
                }
            }
            improve ++;

        }


    }


    private double getTourDistance(List<City> tour) {
        double dist = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            dist += calculateDistance(tour.get(i), tour.get(i+1));
        }
        return dist;
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
//        Map<City, Double> sortedMapAsc = sortByComparator(tour, true);

        System.out.println("Printing Path of Travelling Salesman");
        for (int i = 0; i < tour.size() -1; i++) {
            City c =tour.get(i);
            City c2 = tour.get(i+1);

            System.out.println("("+c.getX()+","+c.getY()+") - ("+c2.getX()+","+c2.getY()+") - "+df.format(calculateDistance(c,c2)));
        }
        System.out.println("Tour distance : "+ df.format(getTourDistance(tour)));
    }

    public void printPath(City c){
        while (c != null){
            System.out.print("("+c.getX()+","+c.getY()+") <-> ");
            c = c.getParent();
        }
    }
}
