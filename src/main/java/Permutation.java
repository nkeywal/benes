import java.util.Arrays;
import java.util.Collections;

public class Permutation implements Cloneable {
  int[] values;


  public Permutation clone() {
    return new Permutation(Arrays.copyOf(values, values.length));
  }

  int getAt(int pos) {
    return values[pos];
  }

  void setAs(int pos, int val) {
    values[pos] = val;
  }

  void swap(int p1, int p2) {
    int tmp = values[p1];
    values[p1] = values[p2];
    values[p2] = tmp;
  }

  static boolean valuesAreDistinct(int[] values) {
    boolean areDistinct = true;

    for (int i = 0; i < values.length; i++) {
      for (int j = i + 1; j < values.length; j++) {
        areDistinct = areDistinct && (values[i] != values[j]);
      }
    }

    return areDistinct;
  }

  static boolean valuesAreInRange(int[] values) {
    for (int value : values) {
      if (value < 0 || value >= values.length) {
        return false;
      }
    }

    return true;
  }

  static boolean isPermutation(int[] values) {
    return valuesAreDistinct(values) && valuesAreInRange(values);
  }

  boolean isPermutation() {
    return valuesAreDistinct(values) && valuesAreInRange(values);
  }


  Permutation(int[] values) {
    if (!isPermutation(values)) {
      throw new IllegalArgumentException("Values are not a permutation");
    }
    this.values = values;
  }

  Permutation(int id) {
    values = new int[id];
    for (int i = 0; i < id; i++) {
      values[i] = i;
    }
  }

  void shuffle() {
    MoreMath.shuffleArray(values);
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Permutation that = (Permutation) o;
    return Arrays.equals(values, that.values);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(values);
  }

  Permutation invert() {
    int[] inverse = new int[values.length];
    for (int i = 0; i < values.length; i++) {
      inverse[this.values[i]] = i;
    }

    return new Permutation(inverse);
  }


  public String toString() {
    return Arrays.toString(values);
  }

}
