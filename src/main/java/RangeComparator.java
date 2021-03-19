import java.util.Comparator;
import java.util.List;

public class RangeComparator implements Comparator<List<Integer>> {

    // sorts range pairs by low value, then high value
    @Override
    public int compare(List<Integer> o1, List<Integer> o2) {
        int lowerCompare = o1.get(0).compareTo(o2.get(0));
        int upperCompare = o1.get(1).compareTo(o2.get(1));

        if (lowerCompare == 0) {
            return upperCompare == 0 ? lowerCompare : upperCompare;
        } else {
            return lowerCompare;
        }
    }
}
