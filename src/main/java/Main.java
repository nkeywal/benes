public class Main {

    public static void main(String[] args) {
        Permutation p = new Permutation(128);
        p.shuffle();

        //p = new Permutation(new int[]{1, 3, 0, 2});
        System.out.println(p);

        RoutingProblem rp = new RoutingProblem(p);

        Solution s = rp.benesRouting();

        System.out.println(s);
        System.out.println((s.isOk() ? " Well done!" : "Problem!"));

        // TikzPathsFromRoutingTable.printTikzPaths(route);
    }
}
