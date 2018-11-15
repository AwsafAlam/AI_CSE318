#include<bits/stdc++.h>
#define NULL_VALUE -999999
#define WHITE 0
#define GREY 1
#define BLACK -1

using namespace std;

typedef vector<int> vi;
typedef pair<int,int> ii;
typedef vector<ii> vii;


int main(){
    vi a;
    for(int i=0 ; i< 20 ; i++){
        a.push_back(i);
    }

    cout<<a.size()<<"  "<<a.front()<<"  "<<a.back()<<endl; //returns 1st and last elements
    for(int i = 0 ; i < a.size() ; i++){
        cout<<a[i]<<"  ";
    }

    a.pop_back();
    a.pop_back();

    cout<<a.size()<<"  "<<a.front()<<"  "<<a.back()<<endl; //returns 1st and last elements

    a.erase(a.begin()+3);
    for(int i = 0 ; i < a.size() ; i++){
        cout<<a[i]<<"  ";
    }
    cout<<endl;
    a.erase(a.begin() , a.begin()+9); // Deletes 10 elements

    for(int i = 0 ; i < a.size() ; i++){
        cout<<a[i]<<"  ";
    }

    a.erase(a.begin() , a.begin()+ a.size()); //Deletes all elements
    cout<<endl;
    for(int i = 0 ; i < a.size() ; i++){
        cout<<a[i]<<"  ";
    }
    a.assign(7, 0);

    cout<<endl<<a.size()<<"  "<<a.front()<<"  "<<a.back()<<endl; //returns 1st and last elements

    a.erase(a.begin() , a.end());

    if(a.empty())
        cout<<"Vector now empty\n";

    std::vector<int> foo (3,100);   // three ints with a value of 100
  std::vector<int> bar (5,200);   // five ints with a value of 200

  foo.swap(bar);

  a.swap(foo);
    cout<<endl<<a.size()<<"  "<<a.front()<<"  "<<a.back()<<endl; //returns 1st and last elements

//a.insert(a.begin()+7, 9); // Cannot insert more than vector size
a.insert(a.begin() , 9);///inserts an item
    cout<<endl<<a.size()<<"  "<<a.front()<<"  "<<a.back()<<endl; //returns 1st and last elements

    a[1] = 900; ///Replacesan item
//  Elements will swap
    for(int i = 0 ; i < a.size() ; i++){
        cout<<a[i]<<"  ";
    }


}