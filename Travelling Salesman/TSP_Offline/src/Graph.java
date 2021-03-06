import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Graph {

    private List<City> cities;
    private static final double INF = 999999999.0;

    private List<City> tour;
    private List<City> new_tour;

    public List<City> getTour() {
        return tour;
    }
    private DecimalFormat df = new DecimalFormat("#.###");

    public Graph(List<City> cities,int bound) {
        this.cities = cities;
        for (int i = 0; i < cities.size(); i++) {
//            System.out.print("("+cities.get(i).getX()+","+cities.get(i).getY()+") : ");

            for (int j = 0; j < bound; j++) {
                // Generate graph for cities
                double dist = calculateDistance(cities.get(i),cities.get(j));
                System.out.print("("+cities.get(j).getX()+","+cities.get(j).getY()+" <-> "+
                        df.format(dist) +")  ");
                cities.get(i).addNeighbour(dist , cities.get(j));

            }

        }
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
//        dist =getDistanceFromLatLonInKm(a.getX(),a.getY() , b.getX() , b.getY());
        return dist;
    }

    double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1); // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }
    double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    public void NearestInsertion(int city){
        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }

        tour = new ArrayList<>();
        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        init.setVisited(true);
        tour.add(init);
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

    public double NearestNeighbour(int city){
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
        return getTourDistance(tour);
    }

    public double NearestNeighbour_Random(int city){
        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }

        tour = new ArrayList<>();
        int n = cities.size();
        City init = cities.get(city); // Starting with the defined city
        init.setVisited(true);
        tour.add(init);
        n--;
        double distance = 0.0;
        City prevTour = init;
        int max = 3, min =1;
        while (n > 0){

            double first = INF;
            double second = INF;
            double third = INF;

            City firstCity = null;
            City secCity = null;
            City thirdCity = null;

            for (int j = 0; j < cities.size(); j++) {

                City c = cities.get(j);
                if(c.isVisited())
                    continue;

                double neighbour_dist = calculateDistance(c,prevTour);
                if(neighbour_dist < first){// Distance from neighbour
                    third = second;
                    thirdCity = secCity;
                    second = first;
                    secCity = firstCity;
                    first = neighbour_dist;
                    firstCity = c;
                }
                else if(neighbour_dist > first && neighbour_dist < second){
                    third = second;
                    thirdCity = secCity;
                    second = neighbour_dist;
                    secCity = c;

                }
                else if(neighbour_dist > first && neighbour_dist> second && neighbour_dist < third){
                    thirdCity = c;
                    third = neighbour_dist;
                }

            }

            if (third == INF){ max = 2;}
            if( second == INF){ max = 1;}

            Random random = new Random();
            int choice  =random.nextInt(max - min + 1) + min;

            if(choice == 1){
                distance += first;
                firstCity.setVisited(true);
                prevTour = firstCity;
                tour.add(firstCity);
            }
            else if(choice == 2){
                distance += second;
                secCity.setVisited(true);
                prevTour = secCity;
                tour.add(secCity);
            }
            else {
                distance += third;
                thirdCity.setVisited(true);
                prevTour = thirdCity;
                tour.add(thirdCity);
            }
            n--;

        }
        return getTourDistance(tour);
    }

    public double SavingsHeuristic_Random(int city){
        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }
        tour = new ArrayList<>();
        Map<Double , Pair<City,City>> Ksavings = new HashMap<>();
        int n = cities.size() , k = city;
        double savingstable[][] = new double[n][n];
        City start = cities.get(city);
        start.setVisited(true);

        double max1 = 0.0 , max2= 0.0;
        Pair<City,City> maxpair=null;

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i == k || i == j || j == k)
                    continue;

                double d = calculateDistance(cities.get(k), cities.get(i)) + calculateDistance(cities.get(k), cities.get(j))
                        - calculateDistance(cities.get(i), cities.get(j));
                Pair<City,City> tmp = new Pair<>(cities.get(i) , cities.get(j)) ;
                savingstable[i][j] = d;
                if(d > max1){
                    max1 = d;
                    maxpair = tmp;
                }
                Ksavings.put(d,tmp);
            }
        }

        Map<Double,Pair<City, City>> sorted = Ksavings
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,LinkedHashMap::new));

        Random random = new Random();
        int choice  =random.nextInt(n);

        Pair<City, City> kpair = (Pair<City, City>) sorted.values().toArray()[choice];

        kpair.getKey().setVisited(true);
        kpair.getValue().setVisited(true);
        tour.add(kpair.getKey());
        tour.add(kpair.getValue());

        while (tour.size() < n-1 ){

            //System.out.println("Savings : "+sav);
            int max1idx=-1,max2idx=-1;

            City front = tour.get(0);
            City back = tour.get(tour.size() - 1);
            int u = cities.indexOf(front);
            int v = cities.indexOf(back);

            //find edge for u
            max1=0;
            for(int i=0;i< n;i++){
                if(!cities.get(i).isVisited())
                {
                    if(savingstable[u][i] > max1)
                        max1=savingstable[u][i]; max1idx=i;
                }
            }

            //find edge for v
            max2=0;
            for(int i=0 ;i< n;i++){
                if(!cities.get(i).isVisited())
                {
                    if(savingstable[v][i] > max2)
                        max2=savingstable[v][i]; max2idx=i;
                }
            }

            if(max1idx==max2idx)
            {

                if(max1 > max2){
                    cities.get(max1idx).setVisited(true); tour.add(0 , cities.get(max1idx));
                }
                else{
                    cities.get(max2idx).setVisited(true); tour.add(cities.get(max2idx));
                }
            }

            else
            {
                cities.get(max1idx).setVisited(true); tour.add(0 , cities.get(max1idx));
                cities.get(max2idx).setVisited(true); tour.add(cities.get(max2idx));
            }

        }
        tour.add(0,start);
        tour.add(start);


        return getTourDistance(tour);

    }

    public double SavingsHeuristic(int city){

        for (City c:cities) {
            c.setVisited(false);
            c.setParent(null);
        }
        tour = new ArrayList<>();
        int n = cities.size() , k = city;
        double savingstable[][] = new double[n][n];
        City start = cities.get(city);
        start.setVisited(true);

        double max1 = 0.0 , max2= 0.0;
        Pair<City,City> maxpair=null;

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i == k || i == j || j == k)
                    continue;

                double d = calculateDistance(cities.get(k), cities.get(i)) + calculateDistance(cities.get(k), cities.get(j))
                        - calculateDistance(cities.get(i), cities.get(j));
                Pair<City,City> tmp = new Pair<>(cities.get(i) , cities.get(j)) ;
                savingstable[i][j] = d;
                if(d > max1){
                    max1 = d;
                    maxpair = tmp;
                }
            }
        }

        maxpair.getKey().setVisited(true);
        maxpair.getValue().setVisited(true);
        tour.add(maxpair.getKey());
        tour.add(maxpair.getValue());

        while (tour.size() < n-1 ){

            //System.out.println("Savings : "+sav);
            int max1idx=-1,max2idx=-1;

            City front = tour.get(0);
            City back = tour.get(tour.size() - 1);
            int u = cities.indexOf(front);
            int v = cities.indexOf(back);

            //find edge for u
            max1=0;
            for(int i=0;i< n;i++){
                if(!cities.get(i).isVisited())
                {
                    if(savingstable[u][i] > max1)
                        max1=savingstable[u][i]; max1idx=i;
                }
            }

            //find edge for v
            max2=0;
            for(int i=0 ;i< n;i++){
                if(!cities.get(i).isVisited())
                {
                    if(savingstable[v][i] > max2)
                        max2=savingstable[v][i]; max2idx=i;
                }
            }

            if(max1idx==max2idx)
            {

                if(max1 > max2){
                    cities.get(max1idx).setVisited(true); tour.add(0 , cities.get(max1idx));
                }
                else{
                    cities.get(max2idx).setVisited(true); tour.add(cities.get(max2idx));
                }
            }

            else
            {
                cities.get(max1idx).setVisited(true); tour.add(0 , cities.get(max1idx));
                cities.get(max2idx).setVisited(true); tour.add(cities.get(max2idx));
            }

        }
        tour.add(0,start);
        tour.add(start);


        return getTourDistance(tour);

    }


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
//                        System.out.println("No Improvement : "+Double.toString(best_distance)+" - "+Double.toString(new_distance)+" it: "+ Integer.toString(iteration));
                    }
                }
            }

            improve ++;

        }
    }

    public double Two_Opt_BestImp(List<City> t, int cut_off, int neigh_num){
//        NearestNeighbour_Random(city);
        tour = t;
        // Get tour size
        int size = tour.size();
        new_tour = new ArrayList<>();

        for (int i=0;i<size;i++)
        {

            new_tour.add(tour.get(i));
        }

        // repeat until no improvement is made
        int improve = 0;


        while ( improve < cut_off )
        {
            double best_distance = getTourDistance(tour);

            for ( int i = 1; i < size - 1; i++ )
            {
                Map<Double , City> Nlist = cities.get(i).getNeighbour();
                int count = 0;
                for (double d:Nlist.keySet()) {
                    City c = Nlist.get(d);
                    int k = cities.indexOf(c);
                    TwoOptSwap( i, k );
                    double new_distance = getTourDistance(new_tour);

                    if ( new_distance < best_distance )
                    {
                        // Improvement found so reset
                        improve = 0;
                        tour = new ArrayList<>();
                        for (int j=0;j<size;j++)
                        {
                            tour.add(j,new_tour.get(j));
                        }

                        best_distance = new_distance;

                        // Update the display
                        System.out.println("Improment found: "+Double.toString(best_distance)+" it: "+ Integer.toString(count));
                    }

                    count++;
                    if(count >= neigh_num){
                        break;
                    }
                }
            }

            improve ++;

        }
        return getTourDistance(tour);
    }

    public double Two_Opt_FirstImp(List<City> t, int cut_off, int neigh_num){
        //NearestNeighbour_Random(city);
        tour = t;
        // Get tour size
        int size = tour.size();
        new_tour = new ArrayList<>();

        for (int i=0;i<size;i++)
        {

            new_tour.add(tour.get(i));
        }

        // repeat until no improvement is made
        int improve = 0;


        while ( improve < cut_off )
        {
            double best_distance = getTourDistance(tour);

            for ( int i = 1; i < size - 1; i++ )
            {
                Map<Double , City> Nlist = cities.get(i).getNeighbour();
                int count = 0;
                for (double d:Nlist.keySet()) {
                    City c = Nlist.get(d);
                    int k = cities.indexOf(c);
                    TwoOptSwap( i, k );
                    double new_distance = getTourDistance(new_tour);

                    if ( new_distance < best_distance )
                    {
                        // Improvement found so reset
                        improve = 0;
                        tour = new ArrayList<>();
                        for (int j=0;j<size;j++)
                        {
                            tour.add(j,new_tour.get(j));
                        }

                        best_distance = new_distance;

                        // Update the display
                        System.out.println("Improment found: "+Double.toString(best_distance)+" it: "+ Integer.toString(count));
                        break;

                    }

                    count++;
                    if(count >= neigh_num){
                        break;
                    }
                }
            }

            improve ++;

        }
        return getTourDistance(tour);
    }

    private void TwoOptSwap( int i, int k )
    {
        int size = tour.size();
        new_tour = new ArrayList<>();
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
                            tour = new ArrayList<>();
                            for (int p=0;p<size;p++)
                            {
                                tour.add(p,new_tour.get(p));
                            }

                            best_distance = new_distance;

                            // Update the display
                            System.out.println("Improment found: "+df.format(best_distance)+" it: "+ Integer.toString(iteration));
                        }
                        else {
//                            System.out.println("No Improvement : "+df.format(best_distance)+" - "+df.format(new_distance)+" it: "+ Integer.toString(iteration));
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


}
