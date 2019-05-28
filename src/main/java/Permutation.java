import java.util.Arrays;

public class Permutation implements Cloneable {
  private int[] values;


  public int getLength(){
    return values.length;
  }

  public Permutation clone() {
    return new Permutation(Arrays.copyOf(values, values.length));
  }

  int getAt(int pos) {
    return values[pos];
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

  boolean isInvert(Permutation inv) {
    return equals(inv.invert());
  }

  public String toString() {
    return Arrays.toString(values);
  }

}
