public class Main {

    public static void main(String[] args) {
        int[] p = {2, 3, 1, 0};

        RoutingProblem rp = new RoutingProblem(p);

        // System.out.print(rp);

        boolean[][] solution = rp.benesRouting();


        System.out.println(RoutingProblem.printSolution(solution));

        // boolean[][] route = {{false, true, true}, {false, true, true}, {false, false, true}, {false, false, true}};

        // TikzPathsFromRoutingTable.printTikzPaths(route);
    }
}
