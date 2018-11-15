#include<stdio.h>
#include<stdlib.h>
#define NULL_VALUE -999999
#define INFINITY 999999
#define WHITE 1
#define GREY 2
#define BLACK 3

class Queue
{
    int queueInitSize ;
    int queueMaxSize;
    int * data;
    int length;
    int front;
    int rear;
public:
    Queue();
    ~Queue();
    void enqueue(int item); //insert item in the queue
    int dequeue(); //returns the item according to FIFO
    bool empty(); //return true if Queue is empty
};

Queue::Queue()
{
    queueInitSize = 2 ;
    queueMaxSize = queueInitSize;
    data = new int[queueMaxSize] ; //allocate initial memory
    length = 0 ;
    front = 0;
    rear = 0;
}


void Queue::enqueue(int item)
{
	if (length == queueMaxSize)
	{
		int * tempData ;
		//allocate new memory space for tempList
		queueMaxSize = 2 * queueMaxSize ;
		tempData = new int[queueMaxSize] ;
		int i, j;
		j = 0;
		for( i = rear; i < length ; i++ )
		{
			tempData[j++] = data[i] ; //copy items from rear
		}
		for( i = 0; i < rear ; i++ )
		{
			tempData[j++] = data[i] ; //copy items before rear
		}
		rear = 0 ;
		front = length ;
		delete[] data ; //free the memory allocated before
		data = tempData ; //make list to point to new memory
	}

	data[front] = item ; //store new item
	front = (front + 1) % queueMaxSize ;
	length++ ;
}


bool Queue::empty()
{
	if(length == 0) return true ;
	else return false ;
}


int Queue::dequeue()
{
	if(length == 0) return NULL_VALUE ;
	int item = data[rear] ;
	rear = (rear + 1) % queueMaxSize ;
	length-- ;
	return item ;
}


Queue::~Queue()
{
    if(data) delete[] data; //deallocate memory
    data = 0; //set to NULL
}

//****************Queue class ends here************************

//****************Dynamic ArrayList class based************************
class ArrayList
{
	int * list;
	int length ;
	int listMaxSize ;
	int listInitSize ;
public:
	ArrayList() ;
	~ArrayList() ;
	int searchItem(int item) ;
    void insertItem(int item) ;
	void removeItem(int item) ;
	void removeItemAt(int item);
	int getItem(int position) ;
	int getLength();
	bool empty();
	void printList();
} ;


ArrayList::ArrayList()
{
	listInitSize = 2 ;
	listMaxSize = listInitSize ;
	list = new int[listMaxSize] ;
	length = 0 ;
}

void ArrayList::insertItem(int newitem)
{
	int * tempList ;
	if (length == listMaxSize)
	{
		//allocate new memory space for tempList
		listMaxSize = 2 * listMaxSize ;
		tempList = new int[listMaxSize] ;
		int i;
        for( i = 0; i < length ; i++ )
        {
            tempList[i] = list[i] ; //copy all items from list to tempList
        }
        delete[] list ; //free the memory allocated before
        list = tempList ; //make list to point to new memory
	};

	list[length] = newitem ; //store new item
	length++ ;
}

int ArrayList::searchItem(int item)
{
	int i = 0;
	for (i = 0; i < length; i++)
	{
		if( list[i] == item ) return i;
	}
	return NULL_VALUE;
}

void ArrayList::removeItemAt(int position) //do not preserve order of items
{
	if ( position < 0 || position >= length ) return ; //nothing to remove
	list[position] = list[length-1] ;
	length-- ;
}


void ArrayList::removeItem(int item)
{
	int position;
	position = searchItem(item) ;
	if ( position == NULL_VALUE ) return ; //nothing to remove
	removeItemAt(position) ;
}


int ArrayList::getItem(int position)
{
	if(position < 0 || position >= length) return NULL_VALUE ;
	return list[position] ;
}

int ArrayList::getLength()
{
	return length ;
}

bool ArrayList::empty()
{
    if(length==0)return true;
    else return false;
}

void ArrayList::printList()
{
    int i;
    for(i=0;i<length;i++)
        printf("%d ", list[i]);
    printf("Current size: %d, current length: %d\n", listMaxSize, length);
}

ArrayList::~ArrayList()
{
    if(list) delete [] list;
    list = 0 ;
}

//******************ArrayList class ends here*************************

//******************Graph class starts here**************************
class Graph
{
	int nVertices, nEdges , count;
	bool directed ;
	ArrayList  * adjList ;
	int *color , *parent , *dist;
	int *discovery , *finish;

	//define other variables required for bfs such as color, parent, and dist
	//you must use pointers and dynamic allocation

public:
	Graph(bool dir = false);
	~Graph();
	void setnVertices(int n);
	void addEdge(int u, int v);
	void removeEdge(int u, int v);
	bool isEdge(int u, int v);
    int getDegree(int u);
    bool hasCommonAdjacent(int u, int v);
    int getDist(int u, int v);
    void printGraph();
	void bfs(int source); //will run bfs in the graph
	void dfs(int source); //will run dfs in the graph
	void visit(int source);
};


Graph::Graph(bool dir)
{
	nVertices = 0 ;
	nEdges = 0 ;
	adjList = 0 ;
	directed = dir ; //set direction of the graph
	color =0; //define other variables to be initialized
    parent=0;
    dist=0;
    discovery=0;
    finish=0;
    count = 1;
}

void Graph::setnVertices(int n)
{
    color = new int[nVertices];
	parent = new int[nVertices];
	dist = new int[nVertices];
	discovery = new int[nVertices];
	finish = new int[nVertices];

	this->nVertices = n ;
	if(adjList!=0) delete[] adjList ; //delete previous list
	adjList = new ArrayList[nVertices] ;
}

void Graph::addEdge(int u, int v)
{
    if(u<0 || v<0 || u>=nVertices || v>=nVertices) return; //vertex out of range
    this->nEdges++ ;
	adjList[u].insertItem(v) ;
	if(!directed) adjList[v].insertItem(u) ;
}

void Graph::removeEdge(int u, int v)
{
    if(u<0 || v<0 || u>=nVertices || v>=nVertices) return; //vertex out of range
    this->nEdges-- ;
	adjList[u].removeItem(v) ;
	if(!directed) adjList[v].removeItem(u) ;
}

bool Graph::isEdge(int u, int v)
{
    if(u<0 || v<0 || u>=nVertices || v>=nVertices) return false; //vertex out of range
    if(adjList[u].searchItem(v)!= NULL_VALUE){
        return true;
    }
    return false;
}

int Graph::getDegree(int u)
{
    if(u<0 || u>=nVertices ) return 0; //vertex out of range

    return adjList[u].getLength();


}

bool Graph::hasCommonAdjacent(int u, int v)
{
    if(u<0 || u>=nVertices || v<0 || v>=nVertices) return false;
    int l,r;
    l = adjList[u].getLength();
    r = adjList[v].getLength();
    if(l>r){
        for(int j=0 ; j<r ;j++){
            for(int i =0 ; i<l ;i ++){
                if(adjList[u].getItem(i) == adjList[v].getItem(j))
                    return true;
            }
        }
    }
    else{
        for(int j=0 ; j<l ;j++){
            for(int i =0 ; i<r ;i ++){
                if(adjList[u].getItem(j) == adjList[v].getItem(i))
                    return true;
            }
        }

    }
    return false;
}

void Graph::bfs(int source)
{
    //complete this function
    //initialize BFS variables
    for(int i=0; i<nVertices; i++)
    {
        color[i] = WHITE ;
        parent[i] = -1 ;
        dist[i] = INFINITY ;
    }
    Queue q ;
    int k=1;
    color[source] = GREY;
    dist[source] = 0 ;
    q.enqueue(source) ;
    while( !q.empty() )
    {
        bool flag = true;
        source = q.dequeue();
        printf("%d  ",source);
        color[source]= 3;
        for(int i=0; i< adjList[source].getLength() ; i++){
            int idx = adjList[source].getItem(i);
            if(color[idx]== 1 ){
                color[idx] = 2;
                dist[idx] = k;
                q.enqueue(idx);
                parent[idx] = source;
                flag = false;
//                printf("%d: %d\n",idx,k);

            }
        }
        if(!flag){
        k++;

        }

    }
    printf("\n");
//    for(int i = 0; i<nVertices ; i++){
//     printf("Dist(%d): %d ",i,dist[i]);
//    }
//    printf("\n");

}
void Graph::visit(int source){
    if(color[source] == 3){
        return;
    }
    discovery[source] = count;
    printf("%d ",source);
    color[source] = 2;
    for( int i = 0 ; i<adjList[source].getLength() ; i++){
        int idx = adjList[source].getItem(i);
        if(color[idx]== 1){
            count++;
            visit(idx);
        }
    }
    color[source] = 3;
    count++;
    finish[source] = count;
    return;
}
void Graph::dfs(int source=0)
{
    count = 1;
    for(int i = 0; i<nVertices ; i++){
        color[i] = WHITE;
    }
    visit(source);

    for(int i = 0; i<nVertices ; i++){
        if(color[i] == WHITE){
            visit(i);
        }
    }
//    for(int i = 0; i<nVertices ; i++){
//     printf("Node: %d ; Color: %d ; Starting time : %d ; Finish : %d\n",i,color[i],discovery[i],finish[i] );
//    }

}

int Graph::getDist(int u, int v)
{

    bfs(u);
    for(int i=0 ;i<nVertices ; i++){
        if(i == v){
            return dist[i];
        }
    }
    //returns the shortest path distance from u to v
    //must call bfs using u as the source vertex, then use distance array to find the distance
    return INFINITY ;
}

void Graph::printGraph()
{
    printf("\nNumber of vertices: %d, Number of edges: %d\n", nVertices, nEdges);
    for(int i=0;i<nVertices;i++)
    {
        printf("%d:", i);
        for(int j=0; j<adjList[i].getLength();j++)
        {
            printf(" %d", adjList[i].getItem(j));
        }
        printf("\n");
    }
}

Graph::~Graph()
{
    if(adjList) delete [] adjList;
    if(color) delete [] color;
    if(parent) delete [] parent;
    if(dist) delete [] dist;
    if(discovery) delete [] discovery;
    if(finish) delete [] finish;

    adjList = 0 ;
    color = 0 ;
    parent = 0 ;
    dist = 0 ;
    discovery = 0 ;
    finish = 0 ;

    //write your destructor here
}


//**********************Graph class ends here******************************


//******main function to test your code*************************
int main(void)
{
    int n;
    Graph g;
    printf("Enter number of vertices: ");
    scanf("%d", &n);
    g.setnVertices(n);

    while(1)
    {
        printf("1. Add edge. 2. Remove edge 3. isEdge\n");
        printf("4. Degree . 5. Print Graph  6. Exit.\n");
        printf("7. Common Adjacent. 8. BFS  9. DFS. 10. Shortest Path\n");

        int ch;
        scanf("%d",&ch);
        if(ch==1)
        {
            int u, v;
            scanf("%d%d", &u, &v);
            g.addEdge(u, v);
        }
        else if(ch==2)
        {
            int u, v;
            scanf("%d%d", &u, &v);
            g.removeEdge(u ,v);

        }
        else if(ch==3)
        {
            int u, v;
            scanf("%d%d", &u, &v);
            if(g.isEdge(u ,v)){
                printf("%d-%d is an edge\n",u,v);
            }
            else{
                printf("%d-%d is not an edge\n",u,v);
            }

        }
        else if(ch==4)
        {
            int u;
            scanf("%d", &u);
            int deg = g.getDegree(u);
            printf("Vertex %d has degree %d\n",u, deg);
        }
        else if(ch==5)
        {
            g.printGraph();
        }
        else if(ch==6)
        {
            break;
        }
        else if(ch==7)
        {
            int u, v;
            scanf("%d%d", &u, &v);
            if(g.hasCommonAdjacent(u, v)){
                printf("%d and %d has common adjacent\n",u,v);
            }
            else{
                printf("%d and %d does not have common adjacent\n",u,v);
            }
        }
        else if(ch==8)
        {
            int u;
            scanf("%d", &u);
            g.bfs(u);
        }
        else if(ch==9)
        {
            int u;
            scanf("%d", &u);
            g.dfs(u);
            printf("\n");
        }
        else if(ch==10)
        {
            int u ,v;
            scanf("%d%d", &u,&v);
            int x = g.getDist(u ,v);
            if(x!= INFINITY){
                printf("Shortest path is => %d\n",x);
            }
            else{
                printf("Graph is not connected\n");
            }
        }
    }

}