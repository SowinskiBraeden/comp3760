package ca.bcit.comp3760.lab04;

import java.util.LinkedList;
import java.util.Queue;

public class Graph
{
    private static final int NO_EDGE = 0;
    private static final int EDGE    = 1;

    private final String[] vertices;
    private final int[][] edges;
    private final boolean directed;

    private String lastDFSOrder;
    private String lastDFSDeadEndOrder;
    private String lastBFSOrder;

    /**
     * Constructs a graph with the given vertex labels and direction setting.
     *
     * @param vertexLabels array of vertex names in internal order
     * @param isDirected true if the graph is directed, false otherwise
     */
    public Graph(final String[] vertexLabels, final boolean isDirected)
    {
        final int n;

        n = vertexLabels.length;

        this.vertices = vertexLabels;
        this.edges = new int[n][n];
        this.directed = isDirected;

        this.lastDFSOrder = null;
        this.lastDFSDeadEndOrder = null;
        this.lastBFSOrder = null;
    }

    /**
     * Returns whether this graph is directed.
     *
     * @return true if directed, false otherwise
     */
    public boolean isDirected()
    {
        return this.directed;
    }

    /**
     * Adds an edge from one vertex to another.
     * If the graph is undirected, the reverse edge is also added.
     * If either vertex label does not exist, nothing happens.
     *
     * @param va starting vertex label
     * @param vb ending vertex label
     */
    public void addEdge(final String va, final String vb)
    {
        final int fromIndex;
        final int toIndex;

        fromIndex = findVertexIndex(va);
        toIndex = findVertexIndex(vb);

        if (fromIndex == -1 || toIndex == -1)
        {
            return;
        }

        this.edges[fromIndex][toIndex] = EDGE;

        if (!this.directed)
        {
            this.edges[toIndex][fromIndex] = EDGE;
        }
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return number of vertices
     */
    public int size()
    {
        return this.vertices.length;
    }

    /**
     * Returns the label of the vertex at the given internal index.
     *
     * @param v internal vertex index
     * @return label of the vertex
     */
    public String getLabel(final int v)
    {
        return this.vertices[v];
    }

    /**
     * Returns a string representation of the graph adjacency matrix.
     *
     * @return adjacency matrix as a string
     */
    @Override
    public String toString()
    {
        final StringBuilder graph;

        graph = new StringBuilder();

        for (int i = 0; i < this.vertices.length; ++i)
        {
            graph.append(this.vertices[i]);
            graph.append(": ");

            for (int j = 0; j < this.vertices.length; ++j)
            {
                graph.append(this.edges[i][j]);

                if (j < this.vertices.length - 1)
                {
                    graph.append(" ");
                }
            }

            if (i < this.vertices.length - 1)
            {
                graph.append("\n");
            }
        }

        return graph.toString();
    }

    private int findVertexIndex(final String label)
    {
        for (int i = 0; i < this.vertices.length; ++i)
        {
            if (this.vertices[i].equals(label))
            {
                return i;
            }
        }

        return -1;
    }

    private void appendVertex(final StringBuilder order, final String label)
    {
        if (order.length() > 0)
        {
            order.append(" ");
        }

        order.append(label);
    }

    private void DFS(final int idx,
                     final boolean[] visited,
                     final boolean quiet,
                     final StringBuilder visitOrder,
                     final StringBuilder deadEndOrder)
    {
        visited[idx] = true;
        appendVertex(visitOrder, this.vertices[idx]);

        if (!quiet)
        {
            System.out.printf("Visiting vertex %s%n", this.vertices[idx]);
        }

        for (int i = 0; i < this.edges[idx].length; ++i)
        {
            if (this.edges[idx][i] == EDGE && !visited[i])
            {
                DFS(i, visited, quiet, visitOrder, deadEndOrder);
            }
        }

        appendVertex(deadEndOrder, this.vertices[idx]);
    }

    /**
     * Runs depth first search using the default first-on-the-list rule.
     * If the graph is disconnected, all vertices are still included.
     *
     * @param quiet true to suppress console output, false to print traversal
     */
    public void runDFS(final boolean quiet)
    {
        final boolean[] visited;
        final StringBuilder visitOrder;
        final StringBuilder deadEndOrder;

        visited = new boolean[this.vertices.length];
        visitOrder = new StringBuilder();
        deadEndOrder = new StringBuilder();

        if (!quiet)
        {
            System.out.println("DFS order traversal of graph:");
        }

        for (int i = 0; i < this.vertices.length; ++i)
        {
            if (!visited[i])
            {
                DFS(i, visited, quiet, visitOrder, deadEndOrder);
            }
        }

        this.lastDFSOrder = visitOrder.toString();
        this.lastDFSDeadEndOrder = deadEndOrder.toString();
    }

    /**
     * Runs depth first search starting at the given vertex.
     * Only vertices reachable from the start vertex are included.
     *
     * @param v starting vertex label
     * @param quiet true to suppress console output, false to print traversal
     */
    public void runDFS(final String v, final boolean quiet)
    {
        final int startIndex;
        final boolean[] visited;
        final StringBuilder visitOrder;
        final StringBuilder deadEndOrder;

        startIndex = findVertexIndex(v);

        if (startIndex == -1)
        {
            return;
        }

        visited = new boolean[this.vertices.length];
        visitOrder = new StringBuilder();
        deadEndOrder = new StringBuilder();

        if (!quiet)
        {
            System.out.println("DFS order traversal of graph:");
        }

        DFS(startIndex, visited, quiet, visitOrder, deadEndOrder);

        this.lastDFSOrder = visitOrder.toString();
        this.lastDFSDeadEndOrder = deadEndOrder.toString();
    }

    private void BFS(final int startIndex,
                     final boolean[] visited,
                     final boolean quiet,
                     final StringBuilder bfsOrder)
    {
        final Queue<Integer> queue;

        queue = new LinkedList<>();

        visited[startIndex] = true;
        queue.offer(startIndex);

        while (!queue.isEmpty())
        {
            final int current;

            current = queue.poll();
            appendVertex(bfsOrder, this.vertices[current]);

            if (!quiet)
            {
                System.out.printf("Visiting vertex %s%n", this.vertices[current]);
            }

            for (int next = 0; next < this.edges[current].length; ++next)
            {
                if (this.edges[current][next] == EDGE && !visited[next])
                {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
    }

    /**
     * Runs breadth first search using the default first-on-the-list rule.
     * If the graph is disconnected, all vertices are still included.
     *
     * @param quiet true to suppress console output, false to print traversal
     */
    public void runBFS(final boolean quiet)
    {
        final boolean[] visited;
        final StringBuilder bfsOrder;

        visited = new boolean[this.vertices.length];
        bfsOrder = new StringBuilder();

        if (!quiet)
        {
            System.out.println("BFS order traversal of graph:");
        }

        for (int i = 0; i < this.vertices.length; ++i)
        {
            if (!visited[i])
            {
                BFS(i, visited, quiet, bfsOrder);
            }
        }

        this.lastBFSOrder = bfsOrder.toString();
    }

    /**
     * Runs breadth first search starting at the given vertex.
     * Only vertices reachable from the start vertex are included.
     *
     * @param v starting vertex label
     * @param quiet true to suppress console output, false to print traversal
     */
    public void runBFS(final String v, final boolean quiet)
    {
        final int startIndex;
        final boolean[] visited;
        final StringBuilder bfsOrder;

        startIndex = findVertexIndex(v);

        if (startIndex == -1)
        {
            return;
        }

        visited = new boolean[this.vertices.length];
        bfsOrder = new StringBuilder();

        if (!quiet)
        {
            System.out.println("BFS order traversal of graph:");
        }

        BFS(startIndex, visited, quiet, bfsOrder);

        this.lastBFSOrder = bfsOrder.toString();
    }

    /**
     * Returns the DFS visit order from the most recently run DFS.
     *
     * @return DFS order string, or a message if DFS has not been run
     */
    public String getLastDFSOrder()
    {
        if (this.lastDFSOrder == null)
        {
            return "No DFS has been run yet.";
        }

        return this.lastDFSOrder;
    }

    /**
     * Returns the DFS dead-end order from the most recently run DFS.
     *
     * @return DFS dead-end order string, or a message if DFS has not been run
     */
    public String getLastDFSDeadEndOrder()
    {
        if (this.lastDFSDeadEndOrder == null)
        {
            return "No DFS has been run yet.";
        }

        return this.lastDFSDeadEndOrder;
    }

    /**
     * Returns the BFS visit order from the most recently run BFS.
     *
     * @return BFS order string, or a message if BFS has not been run
     */
    public String getLastBFSOrder()
    {
        if (this.lastBFSOrder == null)
        {
            return "No BFS has been run yet.";
        }

        return this.lastBFSOrder;
    }
}