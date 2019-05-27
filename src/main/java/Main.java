public class Main {

    public static void main(String[] args) {
        Permutation p = new Permutation(2);
        p.shuffle();

        p = new Permutation(new int[]{2, 3, 0, 1});
        System.out.println(p);

        RoutingProblem rp = new RoutingProblem(p);

        Solution s = rp.benesRouting();

        System.out.println(s);
        System.out.println((s.isOk() ? " Well done!" : "Problem!"));

        s.printRes();

        // boolean[][] route = {{false, true, true}, {false, true, true}, {false, false, true}, {false, false, true}};

        // TikzPathsFromRoutingTable.printTikzPaths(route);
    }
}
