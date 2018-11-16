#include<stdio.h>
#include<iostream>
#include <vector>
// #include "BFS_DFS_adjList.h"
#include "State.h"

using namespace std;

#define LEFT_BANK 1
#define RIGHT_BANK 0


class Graph
{
    State *startState, *goalState;
    vector<State *> openlist;
    vector<State *> closelist;

    int TotalMissionary, TotalCannibal , TotalCapacity;
public:
    Graph(int m, int c , int capacity, State * start);
    ~Graph();

    bool solvable();
    int bfs(State * s);
    int dfs(State * s);
    void dfsVisit(State * s);
    vector<State *> expand(State * s);    
};

Graph::Graph(int m, int c , int capacity, State * start)
{
    startState = start;
    TotalMissionary = m;
    TotalCannibal = c;
    TotalCapacity = capacity;
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
        uncovered->setChildState(nextState);
        uncovered->printState();

        // printf("( %d,%d,%d ) --",uncovered->getMissionary(),uncovered->getCannibal(),uncovered->getSide());
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
                    flag = false;
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

    return k;
}

vector<State *> Graph::expand(State * s){

    int pos = s->getSide();
    int missionary = s->getMissionary();
    int cannibal = s->getCannibal();

    int bMiss = 0;
    int bCann = 0;

    vector<State *> nextStates;
    if(pos == LEFT_BANK){
        
        for (int i = 0; i <= missionary; i++)
        {
            for (int j = 0; j <= cannibal; j++)
            {
                if( i ==0 && j == 0)
                    continue;

                if (i + j > TotalCapacity)
                    break;
                //good state found, so add to agenda
                bMiss = i;
                bCann = j;
                State * child = new State(bMiss , bCann , RIGHT_BANK);
                child->setParent(s);
                nextStates.push_back(child);
                // addStateToAgenda("_" + stateName++, CurState, nMiss, nCan);
            }
        }
    }
    else{
        for (int i = 1; i <= TotalMissionary - missionary; i++)
        {
            for (int j = 1; j <= TotalCannibal - cannibal; j++)
            {
                if( i ==0 && j == 0)
                    continue;
                
                if (i + j > TotalCapacity)
                    break;
                //good state found, so add to agenda
                bMiss = i;
                bCann = j;
                State * child = new State(bMiss , bCann , LEFT_BANK);
                child->setParent(s);
                nextStates.push_back(child);
                // addStateToAgenda("_" + stateName++, CurState, nMiss, nCan);
            }
        }
    }

    return nextStates;
    /*
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
    */

}

Graph::~Graph()
{

}
