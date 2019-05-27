import java.util.ArrayList;
import java.util.List;

public class Solution {
  final Permutation p;
  final int depth;
  final boolean[][] routingTable;

  public Solution(RoutingProblem rp, boolean[][] routingTable) {
    this.p = rp.p.clone();
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

    Permutation cur = new Permutation(p.values.length);
    List<Permutation> res = new ArrayList<>();
    res.add(cur);

    for (int col = 0; col<routingTable[0].length; col++) {
      int[] next = new int[p.values.length];
      int currentJump = (int) Math.pow(2, Math.abs(depth - 1 - col));
      for (int l=0; l<routingTable.length; l++) {
        if (routingTable[l][col]) {
          next[RoutingProblem.jumpBy(l, currentJump)] = cur.values[l];
        } else {
          next[l] = cur.values[l];
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
        System.out.print(res.get(col).values[l]+" ");
      }
      System.out.println();
    }
  }

  public boolean isOk(){
      Permutation cur = new Permutation(p.values.length);

      for (int col = 0; col<routingTable[0].length; col++) {
        int[] next = new int[p.values.length];
        int currentJump = (int) Math.pow(2, Math.abs(depth - 1 - col));
        for (int l=0; l<routingTable.length; l++) {
          if (routingTable[l][col]) {
            next[RoutingProblem.jumpBy(l, currentJump)] = cur.values[l];
          } else {
            next[l] = cur.values[l];
          }
        }

        cur = new Permutation(next);
      }

      return cur.equals(p);
  }

}
