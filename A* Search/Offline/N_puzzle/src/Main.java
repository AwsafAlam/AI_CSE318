import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello World!");

        Scanner sc = new Scanner(new File("input.txt"));
        Scanner scanner = new Scanner(System.in);

        int cutoff = 10000;
        int boardSize = sc.nextInt();

        System.out.println(boardSize);

        int mat[][] = new int[boardSize][boardSize];

        //mat[0][0] = 1;
//        int j =0;
//        while (sc.hasNextLine()){
//            String line = sc.nextLine();
//            String val[] = line.split(" ");
//            if(val.length == 0)
//                continue;
//            for(int i = 0 ; i< val.length ; i++){
//                if(!val[i].equals("")){
//                    mat[j][i] = Integer.parseInt(val[i]);
//                    System.out.print((val[i])+" ");
//                }
//            }
//            System.out.println("");
//            j++;
//        }

        for (int i = 0 ; i< boardSize ; i++){
            for (int j =0; j< boardSize ; j++){
                mat[i][j] = scanner.nextInt();
            }
        }

        Board starting = new Board(boardSize , mat , null , 0);
        Graph g = new Graph(starting , cutoff , boardSize);

        System.out.println(g.getStartboard().toString());

    }
}
