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
        int inv = 0 , blank = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if(mat[i][j] == 0){
                    blank = i;
                    continue;
                }
                // traverse each of the matrix.
                int l = j;
                for (int k = i; k < boardSize; k++) {
                    for (; l < boardSize; l++) {
                        if (mat[i][j] > mat[k][l] && mat[k][l] != 0){
                            System.out.print("("+mat[i][j]+"-"+mat[k][l]+") ");
                            inv++;
                        }
                    }
                    l = 0;
                }

            }
        }
        System.out.println("\nInversions : "+inv);

        if(boardSize%2 != 0){ // Odd board
            if(inv %2 !=0 ){
                System.out.println("Board is not solvable ");
                return;
            }
        }
        else{ //Even board
            System.out.println("Even board");
            if( (inv + blank)%2 == 0 ){
                System.out.println("Board is not solvable ");
                return;
            }
        }


        Board starting = new Board(boardSize , mat , null , 0, 0);
        Graph g = null;

        try {

            g = new Graph(starting , cutoff , boardSize , heuristic_method);
            System.out.println("Running A* search...");
            System.out.println(g.A_Star_Search(starting));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
