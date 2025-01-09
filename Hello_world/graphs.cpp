#include <iostream>
#include <vector>
#include <queue>
#include <climits>
    using namespace std;
// Pair type for convenience
typedef pair<int, int> iPair;
class Graph
{
    int V;                     // Number of vertices
    vector<vector<iPair>> adj; // Adjacency list
public:
    Graph(int V);                           // Constructor
    void addEdge(int v, int w, int weight); // Function to add a weighted edge
    void printGraph();                      // Function to print the graph
    void primMST();                         // Function to find the MST using Prim's algorithm
};
Graph::Graph(int V)
{
    this->V = V;
    adj.resize(V);
}
void Graph::addEdge(int v, int w, int weight)
{
    adj[v].push_back(make_pair(w, weight));
    adj[w].push_back(make_pair(v, weight)); // Since the graph is undirected,add both directions
}
void Graph::printGraph()
{
    for (int v = 0; v < V; ++v)
    {
        cout << "Vertex " << v << ":\n";
        for (auto x : adj[v])
            cout << " -> " << x.first << " (weight " << x.second << ")\n";
        cout << endl;
    }
}
void Graph::primMST()
{
    priority_queue<iPair, vector<iPair>, greater<iPair>> pq;
    int src = 0;                  // Starting vertex
    vector<int> key(V, INT_MAX);  // Minimum weight edge for each vertex
    vector<int> parent(V, -1);    // Store the MST
    vector<bool> inMST(V, false); // To keep track of vertices included in the MST
        // Priority Queue Initialization
        pq.push(make_pair(0, src));
    key[src] = 0;
    while (!pq.empty()) // When the queue is not empty
    {
        int u = pq.top().second;
        pq.pop();
        inMST[u] = true;
        for (auto x : adj[u])
        {
            int v = x.first;
            int weight = x.second;
            if (!inMST[v] && key[v] > weight)
            {
                key[v] = weight;
                pq.push(make_pair(key[v], v));
                parent[v] = u;
            }
        }
    }
    // Print the edges in the MST
    cout << "Edge \tWeight\n";
    for (int i = 1; i < V; ++i)
        cout << parent[i] << " - " << i << " \t" << key[i] << " \n";
}
int main()
{
    Graph g(5);
    g.addEdge(0, 1, 2);
    g.addEdge(0, 4, 6);
    g.addEdge(1, 2, 3);
    g.addEdge(1, 3, 8);
    g.addEdge(1, 4, 5);
    g.addEdge(2, 3, 7);
    g.addEdge(3, 4, 9);
    cout << "Graph representation:\n";
    g.printGraph();
    cout << "\nMinimum Spanning Tree (MST) using Prim's Algorithm:\n";
    g.primMST();
    return 0;
}