#include<stdio.h>
#include<iostream>
#include <vector>
#include <fstream>

// #include "BFS_DFS_adjList.h"
// #include "State.h"
#include "Graph.h"

using namespace std;

#define LEFT_BANK 1
#define RIGHT_BANK 0



int main(){

    // ofstream myfile;
    // myfile.open ("output.txt");
    
    int missionary ,cannibal , boatCap;
    printf("Enter number of man and cannibals and boat capacity\n");

    scanf("%d%d%d", &missionary, &cannibal, &boatCap);

    if(missionary > 0 && cannibal > missionary) {
        cout << "No solution exists ..." <<endl;
        return 0;
    }
  
    State *startState = new State(missionary, cannibal, LEFT_BANK);
    Graph *g = new Graph(missionary, cannibal , boatCap , startState);
    
    // if(!g->solvable()) {
    //     cout << "No solution exists ..." << endl;
    //     return 0;
    // }

    // cout << "Total paths: " << g->bfs(startState)<<endl; // No, of times boat crossed the river
    // cout << "Total paths: " << g->dfs(startState)<<endl; // No, of times boat crossed the river
    g->dfs(startState);
    delete g;
    // myfile.close();

    return 0;
}
