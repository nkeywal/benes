import java.util.ArrayList;
import java.util.List;

public class Solution {
  private final RoutingProblem rp;
  private final int depth;
  final boolean[][] routingTable;

  public Solution(RoutingProblem rp, boolean[][] routingTable) {
    this.rp = rp;
    this.depth = rp.depth;
    this.routingTable = routingTable;
  }


  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (int i=0; i<routingTable.length; i++) {
      for (int j=0; j<routingTable[i].length; j++) {
        sb.append(routingTable[i][j] ? "diag" : "hori" ).append(' ');
      }
      sb.append('\n');
    }
    return sb.toString();
  }

  public List<Permutation> toMove(){

    Permutation cur = new Permutation(rp.p.getLength());
    List<Permutation> res = new ArrayList<>();
    res.add(cur);

    for (int col = 0; col<routingTable[0].length; col++) {
      int[] next = new int[rp.p.getLength()];
      int currentJump = (int) Math.pow(2, Math.abs(depth - 1 - col));
      for (int l=0; l<routingTable.length; l++) {
        if (routingTable[l][col]) {
          next[RoutingProblem.jumpBy(l, currentJump)] = cur.getAt(l);
        } else {
          next[l] = cur.getAt(l);
        }
      }
      cur = new Permutation(next);
      res.add(cur);
    }

    return res;
  }

  public void printRes(){
    List<Permutation> res = toMove();

    for (int l = 0; l < routingTable.length; l++) {
      for (int col = 0; col<=routingTable[0].length; col++) {
        System.out.print(res.get(col).getAt(l)+" ");
      }
      System.out.println();
    }
  }

  public boolean isOk(){
      Permutation cur = new Permutation(rp.p.getLength());

      for (int col = 0; col<routingTable[0].length; col++) {
        int[] next = new int[rp.p.getLength()];
        int currentJump = (int) Math.pow(2, Math.abs(depth - 1 - col));
        for (int l=0; l<routingTable.length; l++) {
          if (routingTable[l][col]) {
            next[RoutingProblem.jumpBy(l, currentJump)] = cur.getAt(l);
          } else {
            next[l] = cur.getAt(l);
          }
        }

        cur = new Permutation(next);
      }

      return cur.invert() .equals(rp.p);
  }

}
