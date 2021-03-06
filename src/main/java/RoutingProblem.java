public class RoutingProblem {
  final int depth;
  private final int card;
  final Permutation p;
  private final Permutation q;

  RoutingProblem(Permutation p) {
    if (p == null || p.getLength() <= 1 || Integer.bitCount(p.getLength()) != 1) {
      throw new IllegalArgumentException("bad length for p");
    }
    card = p.getLength();
    depth = MoreMath.log2(p.getLength());
    this.p = p;
    q = this.p.invert();

    if (!this.p.isInvert(q)) {
      throw new IllegalStateException();
    }
  }

  static int jumpBy(int pos, int jump) {
    int width = 2 * jump;
    int quot = pos / width;
    return width * quot + ((pos + jump) % width);
  }

  static boolean isAbove(int pos, int jump) {
    int width = 2 * jump;
    return ((pos % width) >= jump);
  }

  static int conditionalJumpBy(int pos, int jump, boolean doIt) {
    return doIt ? jumpBy(pos, jump) : pos;
  }

  // for a routing problem of depth d, it produces
  // a router that is a (2^d) x (2 * d - 1)
  // boolean table.
  Solution benesRouting() {
    Solution res = new Solution(this, new boolean[card][2 * depth - 1]);

    Permutation pC = p;
    Permutation qC = q;

    for (int round = 1; round < this.depth; round++) {   // this loop populates all the columns EXCEPT the central one
      boolean[] visited = new boolean[card];      // every round has a new visited boolean table

      int[] pN = new int[card];
      int[] qN = new int[card];

      final int currentJump = (int) Math.pow(2, depth - round);
      for (int i = 0; i < card; i++) {
        boolean xVert = false;      // this could be started randomly; we choose a horizontal start.
        for (int z, x=i; !visited[x]; x = jumpBy(z, currentJump) ) {
          int y = pC.getAt(x);
          int xJump = jumpBy(x, currentJump);
          int yJump = jumpBy(y, currentJump);
          z = qC.getAt(yJump);

          boolean xIsAbove = isAbove(x, currentJump);
          boolean yIsAbove = isAbove(y, currentJump);
          boolean zIsAbove = isAbove(z, currentJump);

          boolean yVert = xVert ^ xIsAbove ^ yIsAbove;
          boolean zVert = zIsAbove ^ (!yIsAbove) ^ yVert;

          res.setRoutingTable(round - 1, x, xJump, xVert);
          res.setRoutingTable(2 * depth - round - 1, y, yJump, yVert);

          int xP = conditionalJumpBy(x, currentJump, xVert);
          int xV = conditionalJumpBy(y, currentJump, yVert);
          int zP = conditionalJumpBy(z, currentJump, zVert);
          int yJV = conditionalJumpBy(yJump, currentJump, yVert);

          pN[xP] = xV;
          qN[xV] = xP;

          pN[zP] = yJV;
          qN[yJV] = zP;

          visited[x] = true;
          visited[z] = true;

          xVert = zVert;
        }
      }

      pC = new Permutation(pN);
      qC = new Permutation(qN);
    }

    // the central row : switch or not
    for (int i = 0; i < card; i++) {
      res.routingTable[i][depth - 1] = (pC.getAt(i) != i);//q.getAt(i));
    }

    return res;
  }
}
