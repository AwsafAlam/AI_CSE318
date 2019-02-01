import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int TOTAL_BINS = 6;
    private static final int DEFAULT_STONES = 4;

    private List<Integer> lowerBin;
    private List<Integer> upperBin;
    private int upperBinStorage;
    private int lowerBinStorage;

    public Board() {
        this.lowerBin = new ArrayList<>();
        this.upperBin = new ArrayList<>();
        this.upperBinStorage = 0;
        this.lowerBinStorage = 0;
        initBoard();
    }

    private void initBoard(){
        for (int i=0; i< TOTAL_BINS ; i++){
            lowerBin.add(4);
            upperBin.add(4);
        }
    }

    public void printBoard(){
        System.out.println("Printing Board:");
        System.out.println("---------------------------\n");
        for (int i = 0; i < TOTAL_BINS; i++) {
            System.out.print("( "+ upperBin.get(i)+" ) ");
        }
        System.out.println("\n---------------------------\n");

        for (int i = 0; i < TOTAL_BINS; i++) {
            System.out.print("( "+ lowerBin.get(i)+" ) ");
        }
        System.out.println("\n---------------------------\nStorage : "+ upperBinStorage +"  Opponent Store : "+ lowerBinStorage);


    }

    public void printtoFile(File file){
        System.out.println("Printing Board to file");
        System.out.println("---------------------------\n");
        for (int i = 0; i < TOTAL_BINS; i++) {
            System.out.print(lowerBin.get(i)+" - ");
        }
        System.out.println("\n---------------------------\n");

        for (int i = 0; i < TOTAL_BINS; i++) {
            System.out.print(upperBin.get(i)+" - ");
        }
        System.out.println("\n---------------------------\nStorage : "+ upperBinStorage +"  Opponent Store : "+ lowerBinStorage);

    }

    public static int getTotalBins() {
        return TOTAL_BINS;
    }

    public static int getDefaultStones() {
        return DEFAULT_STONES;
    }

    public List<Integer> getLowerBin() {
        return lowerBin;
    }

    public void setLowerBin(List<Integer> lowerBin) {
        this.lowerBin = lowerBin;
    }

    public List<Integer> getUpperBin() {
        return upperBin;
    }

    public void setUpperBin(List<Integer> upperBin) {
        this.upperBin = upperBin;
    }

    public int getUpperBinStorage() {
        return upperBinStorage;
    }

    public void setUpperBinStorage(int upperBinStorage) {
        this.upperBinStorage = upperBinStorage;
    }

    public int getLowerBinStorage() {
        return lowerBinStorage;
    }

    public void setLowerBinStorage(int lowerBinStorage) {
        this.lowerBinStorage = lowerBinStorage;
    }

    boolean gameOver(){
        boolean over_upper = true, over_lower = true;
        for (int i = 0; i < TOTAL_BINS; i++) {
            if(upperBin.get(i) != 0 )
                over_upper = false;
        }

        for (int i = 0; i < TOTAL_BINS; i++) {
            if(lowerBin.get(i) != 0 )
                over_lower = false;
        }
        if(over_lower && over_upper){
            if(over_upper)
                collectLowerStones();
            else
                collectUpperStones();

            return true;
        }
        else {
            return false;
        }
    }

    private void collectLowerStones() {
        for (int i = 0; i < TOTAL_BINS; i++) {
            lowerBinStorage += lowerBin.get(i);
        }

    }

    private void collectUpperStones() {
        for (int i = 0; i < TOTAL_BINS; i++) {
            upperBinStorage += upperBin.get(i);
        }
    }
}
