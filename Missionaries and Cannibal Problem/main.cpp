#include<stdio.h>
#include<iostream>
#include <vector>
// #include "BFS_DFS_adjList.h"
#include "State.h"
#include "Graph.h"

using namespace std;

#define LEFT_BANK 1
#define RIGHT_BANK 0



int main(){

    
    int missionary ,cannibal , boatCap;
    printf("Enter number of man and cannibals and boat capacity\n");

    scanf("%d%d%d", &missionary, &cannibal, boatCap);

    if(missionary > 0 && cannibal > missionary) {
        cout << "No solution exists ..." <<endl;
        return 0;
    }
  
    State *startState = new State(missionary, cannibal, LEFT_BANK);
    Graph *g = new Graph(missionary, cannibal , boatCap , startState);
    
    if(!g->solvable()) {
        cout << "No solution exists ..." << endl;
        return 0;
    }

    cout << "Total paths: " << g->bfs(startState) <<endl; // No, of times boat crossed the river


    return 0;
}


// int main(void)
// {
//     int n;
//     Graph g;
//     printf("Enter number of vertices: ");
//     scanf("%d", &n);
//     g.setnVertices(n);

//     while(1)
//     {
//         printf("1. Add edge. 2. Remove edge 3. isEdge\n");
//         printf("4. Degree . 5. Print Graph  6. Exit.\n");
//         printf("7. Common Adjacent. 8. BFS  9. DFS. 10. Shortest Path\n");

//         int ch;
//         scanf("%d",&ch);
//         if(ch==1)
//         {
//             int u, v;
//             scanf("%d%d", &u, &v);
//             g.addEdge(u, v);
//         }
//         else if(ch==2)
//         {
//             int u, v;
//             scanf("%d%d", &u, &v);
//             g.removeEdge(u ,v);

//         }
//         else if(ch==3)
//         {
//             int u, v;
//             scanf("%d%d", &u, &v);
//             if(g.isEdge(u ,v)){
//                 printf("%d-%d is an edge\n",u,v);
//             }
//             else{
//                 printf("%d-%d is not an edge\n",u,v);
//             }

//         }
//         else if(ch==4)
//         {
//             int u;
//             scanf("%d", &u);
//             int deg = g.getDegree(u);
//             printf("Vertex %d has degree %d\n",u, deg);
//         }
//         else if(ch==5)
//         {
//             g.printGraph();
//         }
//         else if(ch==6)
//         {
//             break;
//         }
//         else if(ch==7)
//         {
//             int u, v;
//             scanf("%d%d", &u, &v);
//             if(g.hasCommonAdjacent(u, v)){
//                 printf("%d and %d has common adjacent\n",u,v);
//             }
//             else{
//                 printf("%d and %d does not have common adjacent\n",u,v);
//             }
//         }
//         else if(ch==8)
//         {
//             int u;
//             scanf("%d", &u);
//             g.bfs(u);
//         }
//         else if(ch==9)
//         {
//             int u;
//             scanf("%d", &u);
//             g.dfs(u);
//             printf("\n");
//         }
//         else if(ch==10)
//         {
//             int u ,v;
//             scanf("%d%d", &u,&v);
//             int x = g.getDist(u ,v);
//             if(x!= INFINITY){
//                 printf("Shortest path is => %d\n",x);
//             }
//             else{
//                 printf("Graph is not connected\n");
//             }
//         }
//     }

// }

