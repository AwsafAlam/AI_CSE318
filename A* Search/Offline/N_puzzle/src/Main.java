import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello World!");

        Scanner sc = new Scanner(new File("input.txt"));

        int board = sc.nextInt();
        System.out.println(board);
    }
}
