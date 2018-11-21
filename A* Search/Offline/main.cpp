
#include<bits/stdc++.h>
#include<stdio.h>
#include<iostream>
#include <vector>
#include <fstream>

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
    
    int missionary ,cannibal , boatCap , cutoff;
    printf("Enter number of man and cannibals and boat capacity , and cut-off limit\n");

    scanf("%d%d%d%d", &missionary, &cannibal, &boatCap , &cutoff);

    if(missionary > 0 && cannibal > missionary) {
        cout << "No solution exists ..." <<endl;
        return 0;
    }
  
    State *startState = new State(missionary, cannibal, LEFT_BANK);
    Graph *g = new Graph(missionary, cannibal , boatCap , startState , cutoff);
    
    clock_t start , end;
    
    start = clock();
    cout << "BFS : \n";
    cout<<g->bfs(startState);  // No, of times boat crossed the river
    
    end = clock();
    cout <<"\nBFS time (ms): "<<diffclock(start,end)<<"\n\n";
     
    start = clock();

    cout << "DFS : \n";
    cout<<g->dfs(startState);
    
    end = clock();
    cout <<"\nDFS TIme (ms): "<<diffclock(start,end)<<"\n";
    
    delete g;
   
    return 0;
}
