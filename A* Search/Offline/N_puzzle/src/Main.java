import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello World!");

        Scanner sc = new Scanner(new File("input.txt"));
        Scanner scanner = new Scanner(System.in);

        int cutoff = 1000;
        int boardSize = sc.nextInt();

        System.out.println(boardSize);

        int mat[][] = new int[boardSize][boardSize];

        for (int i = 0 ; i< boardSize ; i++){
            for (int j =0; j< boardSize ; j++){
                mat[i][j] = sc.nextInt();
            }
        }

        Board starting = new Board(boardSize , mat , null , 0, 0);
        Graph g = null;
        try {
            g = new Graph(starting , cutoff , boardSize);
            g.bestFirstSearch(starting , 0);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(g.getStartboard().toString());

    }
}
