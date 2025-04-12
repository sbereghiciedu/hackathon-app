package org.hexed.hackathonapp.math;

import java.util.*;
import java.io.*;
import java.lang.*;

public class MinCostMaxFlow {

    static class Edge {
        int to, f, cap, cost, rev;

        Edge(int v, int cap, int cost, int rev) {
            this.to = v;
            this.cap = cap;
            this.cost = cost;
            this.rev = rev;
        }
    }

    public static List<Edge>[] createGraph(int n) {
        List<Edge>[] graph = new List[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        return graph;
    }

    public static void addEdge(List<Edge>[] graph, int s, int t, int cap, int cost) {
        graph[s].add(new Edge(t, cap, cost, graph[t].size()));
        graph[t].add(new Edge(s, 0, -cost, graph[s].size() - 1));
    }

    static void bellmanFord(List<Edge>[] graph, int s, int[] dist) {
        int n = graph.length;
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[s] = 0;
        boolean[] inqueue = new boolean[n];
        int[] q = new int[n];
        int qt = 0;
        q[qt++] = s;
        for (int qh = 0; (qh - qt) % n != 0; qh++) {
            int u = q[qh % n];
            inqueue[u] = false;
            for (int i = 0; i < graph[u].size(); i++) {
                Edge e = graph[u].get(i);
                if (e.cap <= e.f)
                    continue;
                int v = e.to;
                int ndist = dist[u] + e.cost;
                if (dist[v] > ndist) {
                    dist[v] = ndist;
                    if (!inqueue[v]) {
                        inqueue[v] = true;
                        q[qt++ % n] = v;
                    }
                }
            }
        }
    }

    public static int[] minCostFlow(List<Edge>[] graph, int s, int t, int maxf) {
        int n = graph.length;
        int[] dp = new int[n];
        int[] curflow = new int[n];
        int[] prevedge = new int[n];
        int[] prevnode = new int[n];
        int[] dist = new int[n];

        bellmanFord(graph, s, dist);
        int flow = 0;
        int flowCost = 0;
        while (flow < maxf) {
            PriorityQueue<Long> q = new PriorityQueue<>();
            q.add((long) s);
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[s] = 0;
            boolean[] finished = new boolean[n];
            curflow[s] = Integer.MAX_VALUE;
            while (!finished[t] && !q.isEmpty()) {
                long cur = q.remove();
                int u = (int) (cur & 0xFFFF_FFFFL);
                int priou = (int) (cur >>> 32);
                if (priou != dp[u])
                    continue;
                finished[u] = true;
                for (int i = 0; i < graph[u].size(); i++) {
                    Edge e = graph[u].get(i);
                    if (e.f >= e.cap)
                        continue;
                    int v = e.to;
                    int nprio = dp[u] + e.cost + dist[u] - dist[v];
                    if (dp[v] > nprio) {
                        dp[v] = nprio;
                        q.add(((long) nprio << 32) + v);
                        prevnode[v] = u;
                        prevedge[v] = i;
                        curflow[v] = Math.min(curflow[u], e.cap - e.f);
                    }
                }
            }
            if (dp[t] == Integer.MAX_VALUE)
                break;
            for (int i = 0; i < n; i++)
                if (finished[i])
                    dist[i] += dp[i] - dp[t];
            int df = Math.min(curflow[t], maxf - flow);
            flow += df;
            for (int v = t; v != s; v = prevnode[v]) {
                Edge e = graph[prevnode[v]].get(prevedge[v]);
                e.f += df;
                graph[v].get(e.rev).f -= df;
                flowCost += df * e.cost;
            }
        }

        return new int[]{flow, flowCost};
    }

    public static List<List<Integer>> run(int n, List<InputEdge> egdes){
        int source = 0;
        int destination = n - 1;

        List<Edge>[] graph = createGraph(n);
        for (InputEdge edge : egdes) {
            addEdge(graph, edge.from, edge.to,  edge.capacity, edge.cost);
        }

        int[] maxFlow = minCostFlow(graph, source, destination, Integer.MAX_VALUE);

        List<List<Integer>> solution = new ArrayList<>();
        for (int i = 1; i < n - 1; i++) {
            for (int j = 0; j < graph[i].size(); ++j) {
                Edge edge = graph[i].get(j);
                if (edge.to < n - 1) {
                    if (edge.f > 0) {
                        solution.add(List.of(i, edge.to, edge.f));
                    }
                }
            }
        }
        return solution;
    }
}