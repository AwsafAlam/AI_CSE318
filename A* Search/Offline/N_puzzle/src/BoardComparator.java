import java.util.Comparator;

public class BoardComparator implements Comparator<Board> {

    // Overriding compare()method of Comparator
    // for descending order of cgpa
    public int compare(Board s1, Board s2) {
        if ((s1.getHeuristic()+s1.getDistance()) > (s2.getHeuristic() + s2.getDistance()))
            return 1;
        else if ((s1.getHeuristic()+s1.getDistance()) < (s2.getHeuristic() + s2.getDistance()))
            return -1;
        return 0;
    }


}
