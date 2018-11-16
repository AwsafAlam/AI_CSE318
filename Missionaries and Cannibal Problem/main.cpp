#include<stdio.h>
#include<iostream>
#include <vector>
// #include "BFS_DFS_adjList.h"

using namespace std;

#define LEFT_BANK 1
#define RIGHT_BANK 0


class State
{
    int missionary, cannibal, side; 
    State *parent;
    vector<State *> childStates;
public:
    State(int m, int c, int s);
    int getCannibal(){return cannibal;}
    int getMissionary(){return missionary;}
    int getSide(){return side;}
    bool isvalid();
    bool isGoal();
};


State::State(int m, int c, int s)
{
    missionary = m;
    cannibal = c;
    side = s;
}

bool State::isvalid(){
    return true;
}

bool State::isGoal(){
    if(missionary == 0 && cannibal==0 && side == RIGHT_BANK){return true;}
    else{return false;}
}

class Graph
{
    State *startState, *goalState;
    vector<State *> openlist;
    vector<State *> closelist;

    int TotalMissionary, TotalCannibal;
public:
    Graph(int m, int c);
    ~Graph();

    bool solvable();
    int bfs(State * s);
    int dfs(State * s);
    void dfsVisit(State * s);
    vector<State *> expand(State * s);    
};

Graph::Graph(int m, int c)
{
    startState = new State(m,c,LEFT_BANK);
    TotalMissionary = m;
    TotalCannibal = c;
    goalState = new State(0,0,RIGHT_BANK);
}

bool Graph::solvable(){
    return true;
}

int Graph::bfs(State * s){
    
    int k=1;
    openlist.push_back(s); //color[source] = GREY;

    //dist[source] = 0 ; -> distance covered
    // q.enqueue(source);
    while(!openlist.empty())
    {
        bool flag = true;
        State * uncovered = openlist.front();
        openlist.erase(openlist.begin() , openlist.begin() + 1);

        vector<State *> nextState = expand(uncovered);

        printf("( %d,%d,%d ) --",uncovered->getMissionary(),uncovered->getCannibal(),uncovered->getSide());
        closelist.push_back(uncovered);  //color[source]= BLACK;
        
        
        while(!nextState.empty()){
            State * visit = nextState.front(); //grey node (bfs)
            nextState.erase(nextState.begin() , nextState.begin() + 1);

            if(visit->isvalid()){
                if(visit->isGoal()){
                    return 1000;
                }
                else{
                    openlist.push_back(visit);
                }
            }

        }
        
        // for(int i=0; i< adjList[source].getLength() ; i++){
        //     int idx = adjList[source].getItem(i);
        //     if(color[idx]== 1 ){
        //         color[idx] = 2;
        //         dist[idx] = k;
        //         q.enqueue(idx);
        //         parent[idx] = source;
        //         flag = false;
        //     }
        // }
        if(!flag){
            k++;
        }

    }

    return 12;
}

vector<State *> Graph::expand(State * s){

int pos = s->getSide();
int missionary = s->getMissionary();
int cannibal = s->getCannibal();

vector<State *> nextStates;
if(pos == LEFT_BANK){
    
    int iter = 2;
    while (iter > 0 && cannibal > 0) {
      --cannibal;
      --iter;
      State *s = new State(cannibal, missionary, RIGHT_BANK);
      if(!s->isvalid()) {
        delete[] s;
        continue;
      }
    //   if(search(s, allStates)) {
    //     continue;
    //   }
      nextStates.push_back(s);
    }

    iter = 2;
    while (iter > 0 && missionary > 0) {
      --missionary;
      --iter;
      State *s = new State(cannibal, missionary, RIGHT_BANK);
      if(!s->isvalid()) {
        delete[] s;
        continue;
      }
    //   if(search(s, allStates)) {
    //     continue;
    //   }
      nextStates.push_back(s);
    }

    // num_miss = std::get<1>((*startingState).getStateAttr());
    if(cannibal > 0 && missionary > 0) {
      State *s = new State(cannibal - 1, missionary - 1, RIGHT_BANK);
    //   if(s->isvalid() && !search(s, allStates)) {
      if(s->isvalid()) {
        nextStates.push_back(s);
      } 
      else {
        delete[] s;
      }
    }
}
else{

}


}

Graph::~Graph()
{

}

int main(){

    
    int missionary ,cannibal;
    printf("Enter number of man and cannibals\n");

    scanf("%d%d", &missionary, &cannibal);

    if(missionary > 0 && cannibal > missionary) {
        cout << "No solution exists ..." <<endl;
        return 0;
    }
  
    State *startState = new State(missionary, cannibal, LEFT_BANK);
    Graph *g = new Graph(missionary, cannibal);
    //g.showConnections();
    
    if(!g->solvable()) {
        cout << "No solution exists ..." << endl;
        return 0;
    }

    cout << "Total paths: " << g->bfs(new State(missionary, cannibal, LEFT_BANK)) <<endl; // No, of times boat crossed the river


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

