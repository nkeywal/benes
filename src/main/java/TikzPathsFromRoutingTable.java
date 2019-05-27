public class TikzPathsFromRoutingTable {

    void printTikzPaths (boolean[][] rt) {

        int depth = (rt[0].length+1)/2;

        for (int row=0; row<rt.length; row++) {

            String s = "(node-" + row + "-0)";

            int y = row;

            for (int col=0; col<rt[0].length; col++){

                if (col<depth) {

                    int jump = (int)Math.pow(2, depth - col - 1);

                    y = RoutingProblem.conditionalJumpBy(y, jump, rt[y][col]);

                    s += "-- (node-" + y + "-" + (col+1) +")";

                }

                else {

                    int jump = (int)Math.pow(2, col - depth + 1);

                    y = RoutingProblem.conditionalJumpBy(y, jump, rt[y][col]);

                    s += "-- (node-" + y + "-" + (col+1) +")";

                }
            }

            System.out.println(s);
        }
    }
}
