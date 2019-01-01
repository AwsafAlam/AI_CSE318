import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class main {

    public static void main(String[] args) {

        System.out.println("Starting TSP");

        int graph[][] = { { 0, 10, 15, 20 },
            { 10, 0, 35, 25 },
            { 15, 35, 0, 30 },
            { 20, 25, 30, 0 } };
        int s = 0;
//        cout << travllingSa lesmanProblem(graph, s) << endl;

    }

    static int travllingSalesmanProblem(int graph[][], int s, int V)
    {
        // store all vertex apart from source vertex
//        vector<int> vertex;
        List<Integer> vertex = new ArrayList<>();
        for (int i = 0; i < V; i++)
            if (i != s)
                vertex.add(i);

        // store minimum weight Hamiltonian Cycle.
        int min_path = INT_MAX;
        do {

            // store current Path weight(cost)
            int current_pathweight = 0;

            // compute current path weight
            int k = s;
            for (int i = 0; i < vertex.size(); i++) {
                current_pathweight += graph[k][vertex[i]];
                k = vertex[i];
            }
            current_pathweight += graph[k][s];

            // update minimum
            min_path = min(min_path, current_pathweight);

        } while (next_permutation(vertex.begin(), vertex.end()));

        return min_path;
    }
}
