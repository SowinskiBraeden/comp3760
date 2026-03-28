package ca.bcit.comp3760.lab04;

public class GraphDriver
{
    public static void main(final String[] args)
    {
        final Graph G;
        G = new Graph(new String[]{"a", "b", "c", "d", "e", "f", "g", "h"}, false);

        G.addEdge("a", "b");
        G.addEdge("a", "e");
        G.addEdge("a", "f");
        G.addEdge("b", "f");
        G.addEdge("b", "g");
        G.addEdge("c", "d");
        G.addEdge("c", "g");
        G.addEdge("d", "h");
        G.addEdge("e", "f");
        G.addEdge("g", "h");

        System.out.printf("%s\n\n", G.toString());

        G.runDFS(false);

        System.out.println("\n");

        G.runBFS(false);
    }
}
