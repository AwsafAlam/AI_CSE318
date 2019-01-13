import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Execute {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Startin Travelling salesman");

        Scanner sc = new Scanner(new File("input.txt"));
        Scanner scanner = new Scanner(System.in);
        List<City> cities = new ArrayList<>();
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            cities.add(new City(x,y));
        }
        Graph g = new Graph(cities);


        System.out.println("Choose Heuristic to run :\n" +
                "1. Nearest Insertion\n" +
                "2. Cheapest Insertion\n" +
                "3. Nearest Neighbour\n" +
                "4. Savings Heuristic\n" +
                "5. k-OPT for k=2 First Improvement\n" +
                "6. 2-OPT Best Improvement\n"+
                "7. k-OPT for k=3\n" +
                "-----------------------------------");
        int choice = scanner.nextInt();

        Random random = new Random();
        int city  =random.nextInt(n);

        double min = 9999999.9 , max = 0.0;
        int minC = 0, maxC = 0;


        if(choice == 1){
            System.out.println("Statring at city: "+ (city+1) +"-> ("+cities.get(city).getX()+","+cities.get(city).getY()+")");
            System.out.println("Running Nearest Insertion");
            g.NearestInsertion(city);
            g.printPath();
        }
        else if(choice == 2){
            System.out.println("Statring at city: "+ (city+1) +"-> ("+cities.get(city).getX()+","+cities.get(city).getY()+")");
            System.out.println("Running Cheapest Insertion");
            g.CheapestInsertion(city);
            g.printPath();
        }
        else if(choice == 3){
            /// Task -1
            max = 0.0;
            for (int i = 0; i < 5; i++) {
                city  =random.nextInt(n);
                System.out.println("Statring at city: "+ (city+1) +"-> ("+cities.get(city).getX()+","+cities.get(city).getY()+")");

                System.out.println("Running Nearest Neighbour");
                double dist =  g.NearestNeighbour(city);
                //g.printPath();
                if(dist < min){
                    min = dist;
                    minC = city+1;
                }
                if(dist > max){
                    max = dist;
                    maxC = city+1;
                }
            }
            System.out.println("Best Case : City- "+minC+" : "+min+" Worst case: City- "+maxC+"-"+max);
            min = 9999999.9 ; max = 0.0;
            double avg=0;
            List<City> tour = null;
            for (int i = 0; i < 10; i++) {
                System.out.println("Running Nearest Neighbour Random");
                double dist = g.NearestNeighbour_Random(minC-1);
                avg += dist;
                if(dist < min){
                    min = dist;
                    tour = g.getTour();
                    //minC = city+1;
                }
                if(dist > max){
                    max = dist;
                    //maxC = city+1;
                }
            }
            System.out.println("Best Case : "+min+" Worst case: "+max+" Avg Case: "+(avg/10.0));
            //g.printPath();
            System.out.println("Running 2 OPT First Improve...");
            g.Two_Opt_FirstImp(tour,100 , 7);
            g.printPath();

            System.out.println("Running 2 OPT Best...");
            g.NearestNeighbour_Random(city);
            g.Two_Opt_BestImp(tour,100 , 7);
            ////cut-off is the number of exchanges without improvement
            g.printPath();
        }
        else if(choice == 4){
            min = 9999999.9 ; max = 0.0;
            minC = 0; maxC = 0;
            for (int i = 0; i < 5; i++) {
                city  =random.nextInt(n);
                System.out.println("Statring at city: "+ (city+1) +"-> ("+cities.get(city).getX()+","+cities.get(city).getY()+")");

                System.out.println("Running Savings Heuristic");
                double dist =  g.SavingsHeuristic(city);
                //g.printPath();

                if(dist < min){
                    min = dist;
                    minC = city+1;
                }
                if(dist > max){
                    max = dist;
                    maxC = city+1;
                }
            }
            System.out.println("Best Case : City- "+minC+"-"+min+" Worst case: City- "+maxC+"-"+max);
            min = 9999999.9 ; max = 0.0;
            double avg=0;
            List<City> tour = null;
            for (int i = 0; i < 10; i++) {
                System.out.println("Running Savings Random");
                double dist = g.SavingsHeuristic_Random(minC-1);
                avg += dist;
                if(dist < min){
                    min = dist;
                    tour = g.getTour();
                    //minC = city+1;
                }
                if(dist > max){
                    max = dist;
                    //maxC = city+1;
                }
            }
            System.out.println("Best Case : "+min+" Worst case: "+max+" Avg Case: "+(avg/10.0));
            System.out.println("Running 2 OPT First Improve...");
            g.Two_Opt_FirstImp(tour,100 , 7);
            g.printPath();

            System.out.println("Running 2 OPT Best...");
            g.NearestNeighbour_Random(city);
            g.Two_Opt_BestImp(tour,100 , 7);
            ////cut-off is the number of exchanges without improvement
            g.printPath();
        }
        else if(choice == 5){
            System.out.println("Statring at city: "+ (city+1) +"-> ("+cities.get(city).getX()+","+cities.get(city).getY()+")");
            System.out.println("Running 2 OPT ...");
            g.NearestNeighbour_Random(city);
            g.Two_Opt_FirstImp(g.getTour(), 100 , 7);
             ////cut-off is the number of exchanges without improvement
            g.printPath();
        }
        else if(choice == 6){
            System.out.println("Statring at city: "+ (city+1) +"-> ("
                    +cities.get(city).getX()+","+cities.get(city).getY()+")");
            System.out.println("Running 2 OPT ...");
            g.NearestNeighbour_Random(city);
            g.Two_Opt_BestImp(g.getTour(), 100 , 7);
            ////cut-off is the number of exchanges without improvement
            g.printPath();
        }
        else if(choice == 7){
            System.out.println("Statring at city: "+ (city+1) +"-> ("+cities.get(city).getX()+","+cities.get(city).getY()+")");
            System.out.println("Running 3-OPT ...");
            g.Three_Opt(city,50);
            g.printPath();

        }


    }


}
