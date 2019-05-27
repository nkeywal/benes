public class MoreMath {

    public static int log2(int n) {
        return 31 - Integer.numberOfLeadingZeros(n);
    }
}
