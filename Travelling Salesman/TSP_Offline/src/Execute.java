import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Execute {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Startin Travelling salesman");

        Scanner sc = new Scanner(new File("input.txt"));
        List<City> cities = new ArrayList<>();
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            cities.add(new City(x,y));
        }
        Graph g = new Graph(cities);

        g.printGraph();
        System.out.println("Running Nearest Insertion");
        g.NearestInsertion(0);
        g.printPath();

        System.out.println("Running Cheapest Insertion");
        g.CheapestInsertion(0);
        g.printPath();

        System.out.println("Running Nearest Neighbour");
        g.NearestNeighbour(0);
        g.printPath();

        System.out.println("Running 2 OPT ...");
        g.Two_Opt(0 , 100);
        // cut-off is the number of exchanges without improvement
        g.printPath();

        System.out.println("Running 3-OPT ...");
        g.Three_Opt(0,50);
        g.printPath();

    }


}
