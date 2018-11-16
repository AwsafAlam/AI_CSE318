#include <bits/stdc++.h>
#include <vector>
#include <iostream>
#include <fstream>

#define LEFT_BANK 1
#define RIGHT_BANK 0

using namespace std;

class State
{
    int missionary, cannibal, side; 
    State *parent;
    vector<State *> childStates;
    // ofstream myfile;

public:
    State(int m, int c, int s);
    ~State();

    int getCannibal(){return cannibal;}
    int getMissionary(){return missionary;}
    int getSide(){return side;}
    void setChildState(vector<State *> s){childStates = s;}
    void setParent(State * s){parent = s;}
    State * getParent(){return parent;}
    bool isvalid();
    bool isGoal();
    void printState(){
        printf(" (%d,%d,%d) ",missionary,cannibal,side);
        //myfile<<" ("<<missionary<<","<<cannibal<<","<<side<<") ";    
    }
};


State::State(int m, int c, int s)
{
    missionary = m;
    cannibal = c;
    side = s;

    // myfile.open ("output.txt");
}

bool State::isvalid(){
    if(missionary >= cannibal)
        return true;
    else
        return false;
}

bool State::isGoal(){
    if(missionary == 0 && cannibal==0 && side == RIGHT_BANK){return true;}
    else{return false;}
}

State::~State()
{
    //myfile.close();

}
