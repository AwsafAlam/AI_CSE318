import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("input.txt"));

        int boardSize = sc.nextInt();
        int cutoff = sc.nextInt();
        int heuristic_method = sc.nextInt();

        int mat[][] = new int[boardSize][boardSize];

        for (int i = 0 ; i< boardSize ; i++){
            for (int j =0; j< boardSize ; j++){
                mat[i][j] = sc.nextInt();
            }
        }

        Board starting = new Board(boardSize , mat , null , 0, 0);
        Graph g = null;

        try {

            g = new Graph(starting , cutoff , boardSize , heuristic_method);
            System.out.println(g.bestFirstSearch(starting , 0));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
