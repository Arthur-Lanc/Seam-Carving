import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.Digraph;


public class TestTopo {
    public void testTopo() {
        Digraph digraph = new Digraph(9);
        digraph.addEdge(0, 3);
        digraph.addEdge(0, 4);
        digraph.addEdge(1, 3);
        digraph.addEdge(1, 4);
        digraph.addEdge(1, 5);
        digraph.addEdge(2, 4);
        digraph.addEdge(2, 5);
        digraph.addEdge(3, 6);
        digraph.addEdge(3, 7);
        digraph.addEdge(4, 6);
        digraph.addEdge(4, 7);
        digraph.addEdge(4, 8);
        digraph.addEdge(5, 7);
        digraph.addEdge(5, 8);
        Topological topological = new Topological(digraph);
        for (int e : topological.order()) {
            System.out.print(e + " ");
        }
    }

    public static void main(String args[]) {
            TestTopo testSeamCarver = new TestTopo();
            testSeamCarver.testTopo();
    }
}