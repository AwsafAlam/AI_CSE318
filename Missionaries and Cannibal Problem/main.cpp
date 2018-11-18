
#include<bits/stdc++.h>
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

double diffclock( clock_t clock1, clock_t clock2 ) {

    double diffticks = clock2 - clock1;
    double diffms    = diffticks / ( CLOCKS_PER_SEC  );

    return diffms;
}

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
    
    clock_t start , end;
    
    start = clock();
    cout << "BFS : \n" << g->bfs(startState)<<endl; // No, of times boat crossed the river
    
    end = clock();
    cout <<"\nBFS time (ms): "<<diffclock(start,end)<<"\n";
     
    start = clock();

    g->dfs(startState);
    
    end = clock();
    cout <<"\nDFS TIme (ms): "<<diffclock(start,end)<<"\n";
    
    delete g;
    // myfile.close();

    return 0;
}
