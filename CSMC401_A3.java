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
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class CSMC401_A3 {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

      //List of list        name       data structure 
        List<List<int[]>> adjList = new ArrayList<>(N +1);
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            int capacity = sc.nextInt();

            adjList.get(u).add(new int[]{v, cost, capacity});

            adjList.get(v).add(new int[]{u, cost, capacity});
        }

        int s = 1;
        int dest = 2;

        // 1. Find which highways can be part of he cheapest route
            //shortest path algorithm: dijkstras because:
            // single source, nonnegative

        //INITIALIZE-SINGLE-SOURCE (G, s)
        int[] d = new int[N + 1];
        Arrays.fill(d, Integer.MAX_VALUE);

        int[] pi = new int[N + 1];
        Arrays.fill(pi, -1);

        d[s] = 0;

        //S = 0
        boolean[] S = new boolean[N+1];
        
        //Q = G.V & EXTRACT-MIN(Q)
        PriorityQueue<int[]> Q = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        Q.add(new int[]{d[s], s});
    
        // while Q != 0
        while(!Q.isEmpty()) {
            //u = EXTRACT-MIN(Q)
            int[] current = Q.poll();
            int currentDist = current[0];
            int u = current[1];

            //Skip vertex if processed
            if (S[u]) continue;

            //S = S U {u}
            S[u] = true;

            if (u == dest) break;

            // for each vertex v in G.Adj[u]
            for (int[] edge : adjList.get(u)) {
                int v = edge[0];
                int weight = edge[1];

                //RELAX
                if (!S[v] && d[v] > d[u] + weight) {
                    d[v] = d[u] + weight;
                    pi[v] = u;
                    Q.add(new int[]{d[v], v});
                }
            }

        }

        //2. build a directed graph from those highways only

        //3. solve with max-flow
    }
}
