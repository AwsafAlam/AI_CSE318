#include<stdio.h>
#include<iostream>
#include <vector>
#include <fstream>
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
    ofstream myfile;

    int TotalMissionary, TotalCannibal , TotalCapacity;
public:
    Graph(int m, int c , int capacity, State * start);
    ~Graph();

    bool solvable();
    int bfs(State * s);
    bool searchClosedList(State * s);
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

    myfile.open ("output.txt");

}

bool Graph::solvable(){
    return true;
}

int Graph::bfs(State * s){
    
    int k=1;
    s->setParent(NULL);
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
        // uncovered->printState();
        printf("\n --> ");

        myfile<<" ("<<uncovered->getMissionary()<<","<<uncovered->getCannibal()<<","<<uncovered->getSide()<<") Dist: "<<uncovered->getDistance();    

        myfile << "\n --> ";

        // printf("( %d,%d,%d ) --",uncovered->getMissionary(),uncovered->getCannibal(),uncovered->getSide());
        closelist.push_back(uncovered);  //color[source]= BLACK;
        
        
        while(!nextState.empty()){
            State * visit = nextState.front(); //grey node (bfs)
            nextState.erase(nextState.begin() , nextState.begin() + 1);
            visit->printState();
            myfile<<" ("<<visit->getMissionary()<<","<<visit->getCannibal()<<","<<visit->getSide()<<") ";    
            
            if(visit->isvalid()){
                // visit->setDistance(visit->getParent()->getDistance() + 1);

                if(visit->isGoal()){
                    myfile<<"\nBFS dist : "<<visit->getDistance()<<endl;
                    myfile<<"Path : ";
                    State * temp = visit->getParent(); 
                    while(temp != NULL){
                        myfile<<" ("<<temp->getMissionary()<<","<<temp->getCannibal()<<","<<temp->getSide()<<")  -- ";    
                        temp = temp->getParent();
                    }

                    return visit->getDistance();
                }
                else{
                    openlist.push_back(visit);

                    flag = false;
                }
            }

        }
        // printf("\n -------------------------\n");
        myfile<<"\n -------------------------\n";
        
        if(!flag){
            k++;
        }

    }

    return k;
}

bool Graph::searchClosedList(State * s){

    
    for(int i = 0; i < closelist.size(); i++)
    {
        /* code */
        if(closelist[i]->getCannibal() == s->getCannibal() && closelist[i]->getMissionary() == s->getMissionary() && closelist[i]->getSide() == s->getSide()){
            return true;
        }
    }
    

    return false;
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
            for (int j = 0; j <= cannibal ; j++)
            {
                // if( i ==0 && j == 0)
                //     continue;

                if ((missionary - i) + (cannibal - j) > TotalCapacity || ((missionary - i) + (cannibal - j) ) == 0 )
                    continue;
                
                bMiss = i;
                bCann = j;
                State * child = new State(bMiss , bCann , RIGHT_BANK);
                if(child->isvalid() && !searchClosedList(child)){
                    child->setParent(s);
                    child->setDistance(s->getDistance()+1);
                    nextStates.push_back(child);
                }
                else{
                    delete child;
                }
            }
        }
    }
    else{
        for (int i = 0; i <= TotalMissionary - missionary; i++) //Total - miss in other bank
        {
            for (int j = 0; j <= TotalCannibal - cannibal; j++)
            {
                if( i ==0 && j == 0)
                    continue;
                
                // if ((missionary - i) + (cannibal - j) > TotalCapacity || ((missionary - i) + (cannibal - j) ) == 0 )
                //     continue;
                
                if ( i+j > TotalCapacity)
                    continue;
                
                //i , j = no. of cann/missionary moved to boat
                bMiss = missionary + i;
                bCann = cannibal + j;
                State * child = new State(bMiss , bCann , LEFT_BANK);
                if(child->isvalid()  && !searchClosedList(child)){
                    child->setParent(s);
                    child->setDistance(s->getDistance() + 1);
                    nextStates.push_back(child);
                }
                else{
                    delete child;
                }
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
    myfile.close();

}
