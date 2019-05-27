public class RoutingProblem {
  final int depth;
  final int card;
  final Permutation p;
  final Permutation q;

  RoutingProblem(int[] p) {
    if (p == null || p.length <= 1 || Integer.bitCount(p.length) != 1) {
      throw new IllegalArgumentException("bad length for p: " + p.length);
    }
    card = p.length;
    depth = MoreMath.log2(p.length);
    this.p = new Permutation(p);
    q = this.p.invert();
  }

  RoutingProblem(Permutation p) {
    this(p.values);
  }

  static int jumpBy(int pos, int jump) {
    int width = 2 * jump;
    int quot = pos / width;
    return width * quot + ((pos + jump) % width);
  }

  static boolean isAbove(int x, int jump) {
    int width = 2 * jump;
    return ((x % width) >= jump);
  }

  static int conditionalJumpBy(int pos, int jump, boolean doIt) {
    return doIt ? jumpBy(pos, jump) : pos;
  }

  // for a routing problem of depth d, it produces
  // a router that is a (2^d) x (2 * d - 1)
  // boolean table.
  Solution benesRouting() {
    Solution res = new Solution(this, new boolean[card][2 * depth - 1]);

    for (int round = 1; round
      < this.depth; round++) {   // this loop populates all the columns EXCEPT the central one
      boolean[] visited = new boolean[card];      // every round has a new visited boolean table

      int currentJump = (int) Math.pow(2, depth - round);
      for (int i = 0; i < card; i++) {
        if (!visited[i]) {
          int x = i;
          boolean xVert =
            false;      // this could be started randomly; we choose a horizontal start.
          while (!visited[x]) {
            int y = p.getAt(x);
            int xJump = jumpBy(x, currentJump);
            int yJump = jumpBy(y, currentJump);

            boolean xIsAbove = isAbove(x, currentJump);
            boolean yIsAbove = isAbove(y, currentJump);

            boolean yVert = xVert ^ xIsAbove ^ yIsAbove;

            int z = q.getAt(yJump);
            boolean zIsAbove = isAbove(z, currentJump);

            boolean zVert = zIsAbove ^ (!yIsAbove) ^ yVert;

            res.routingTable[x][round - 1] = xVert;
            res.routingTable[xJump][round - 1] = xVert;
            res.routingTable[y][2 * depth - round - 1] = yVert;
            res.routingTable[yJump][2 * depth - round - 1] = yVert;

            // updating p at x and z
            p.setAs(conditionalJumpBy(x, currentJump, xVert),
              conditionalJumpBy(y, currentJump, yVert));
            p.setAs(conditionalJumpBy(z, currentJump, zVert),
              conditionalJumpBy(yJump, currentJump, yVert));
            if (!p.isPermutation()) {
              throw new IllegalStateException();
            }

            // updating q at y and yJump
            q.setAs(conditionalJumpBy(y, currentJump, yVert),
              conditionalJumpBy(x, currentJump, xVert));
            q.setAs(conditionalJumpBy(yJump, currentJump, yVert),
              conditionalJumpBy(z, currentJump, zVert));
            if (!q.isPermutation()) {
              throw new IllegalStateException();
            }

            visited[x] = true;
            visited[z] = true;

            x = jumpBy(z, currentJump);
            xVert = zVert;
          }
        }
      }
    }

    // the central row : switch or not
    for (int i = 0; i < card; i++) {
      res.routingTable[i][depth - 1] = (p.getAt(i) == i);
    }

    return res;
  }
}
