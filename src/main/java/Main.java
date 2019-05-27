public class Main {

    public static void main(String[] args) {
        // write your code here

        int[] p = {3,2,0,1};

        int[] q = {2,3,0,1};

        int depth = 2;

        RoutingProblem rp = new RoutingProblem(depth, p, q);

        boolean[][] solution = new boolean[4][3];

        solution = rp.benesRouting(rp);

        // System.out.println(routing);

        // boolean[][] route = {{false, true, true}, {false, true, true}, {false, false, true}, {false, false, true}};

        // TikzPathsFromRoutingTable.printTikzPaths(route);
    }
}
