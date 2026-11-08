//Convoy road trip

//Known:
    // -cost of gas per route
    // -capacity of trucks per route

//Find: 
    // max trucks so every truck takes cheapest route

//Data format:

// 1 number of nodes N>=3, N<=1000
// 2 number of edges between cities M
// 3 city number, city number, cost of route, capacity

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class CSMC401_A3 {
    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

      // ADJACENCY LIST IMPLEMENTATION OF DIJKSTRAS
      //List of list        name       data structure 
        List<List<int[]>> adjList = new ArrayList<>(N +1);
        for (int i = 0; i <= N; i++) {
            adjList.add(new ArrayList<>());
        }


        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            int capacity = sc.nextInt();

            adjList.get(u).add(new int[]{v, cost, capacity});
        }

        int s = 1;
        int dest = 2;
        int t = dest;

        // 1. Find which highways can be part of he cheapest route
            //shortest path algorithm: dijkstras because:
            // single source, nonnegative

        //INITIALIZE-SINGLE-SOURCE (G, s)
        int[] d = new int[N + 1];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[s] = 0;

        int[] prev = new int[N + 1];
        Arrays.fill(prev, -1);

        //S = 0
        boolean[] visited = new boolean[N+1];
        
        //Q = G.V & EXTRACT-MIN(Q)
        PriorityQueue<int[]> Q = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        Q.add(new int[]{d[s], s});
    
        // while Q != 0
        while(!Q.isEmpty()) {
            //u = EXTRACT-MIN(Q)
            int[] current = Q.poll();
            @SuppressWarnings("unused")
            int currentDist = current[0];
            int u = current[1];

            //Skip vertex if processed
            if (visited[u]) continue;

            //S = S U {u}
            visited[u] = true;

            if (u == dest) break;

            // for each vertex v in G.Adj[u]
            for (int[] edge : adjList.get(u)) {
                int v = edge[0];
                int cost = edge[1];

                //RELAX
                if (!visited[v] && d[v] > d[u] + cost) {
                    d[v] = d[u] + cost;
                    prev[v] = u;
                    Q.add(new int[]{d[v], v});

                    prev[v] = u;
                }
            }

        }

        //2. build a directed graph from those highways only
        int[][] capacity = new int[N + 1][N + 1];
        List<List<Integer>> flowAdj = new ArrayList<>(N + 1);
        for (int i = 0; i <= N; i++) {
            flowAdj.add(new ArrayList<>());
        }

        for (int u = 1; u <= N; u++) {
            if (d[u] == Integer.MAX_VALUE) continue;

            for (int[] edge : adjList.get(u)) {
                int v = edge[0];
                int cost = edge[1];
                int cap = edge[2];

                // Include edge if it belongs to shortest path
                if (d[u] + cost == d[v]) {
                    if (capacity[u][v] == 0 && capacity[v][u] == 0) {
                        flowAdj.get(u).add(v);
                        flowAdj.get(v).add(u);
                    }
                    capacity[u][v] += cap;
                }
            }
        }
        //3. solve with max-flow EDMUND -KARP INT0 FORD-FULKERSON
        int maxFlow = 0;
        int[] parent = new int[N + 1];

        while (true) {
            //BFS with augmenting path/residual capacity
            Arrays.fill(parent, -1);
            Queue<Integer> q = new LinkedList<>();
            q.add(s);
            parent[s] = s;

            while (!q.isEmpty() && parent[t] == -1) {
                int u = q.poll();
                for (int v : flowAdj.get(u)) {
                    if (parent[v] == -1 && capacity[u][v] > 0) {
                        parent[v] = u;
                        q.add(v);
                    }
                }
            }
            
            //stop when reaching to la
            if (parent[t] == -1) break;

            //Find bottleneck along augmentedf path
            int pathFlow = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v]);
            }

            //Update residual capacities along the path
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                capacity[u][v] -= pathFlow;
                capacity[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }
        System.out.println(maxFlow);

    }
}
