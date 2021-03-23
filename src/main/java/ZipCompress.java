
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class ZipCompress {

    /**
     * to be run from terminal
     * @param args a list of any number of numbers
     */
    public static void main(String[] args) {

        List<Integer> intList = Arrays.stream(args).map(Integer::parseInt).collect(Collectors.toList());
        List<List<Integer>> rangePairList = makeRangePairList(intList);
        System.out.println(process(rangePairList).toString());

    }

    /**
     * The main processing utility.
     * Takes in list or range-pairs to first sanitize, then sort, finally to compress and deliver.
     * @param zipList Input list of values already organized into range-pairs
     * @return compressedList fully processed list ready to return to user
     */
    public static List<List<Integer>> process(List<List<Integer>> zipList) {

        if (zipList == null) return emptyList();
        zipList.removeIf(ZipCompress::validateParameters);
        if (zipList.isEmpty()) return emptyList();

        zipList.forEach(Collections::sort); // puts each range pair in proper (low, high) order
        zipList.sort(new RangeComparator());

        return compressList(zipList);
    }

    /**
     *  Takes in a list of range-pairs and produces a minimized version that is functionally identical.
     *  Input must be fully sorted, and absent of null values, miss-sized ranges, or out of scope values.
     * @param rawList   a sanitized sorted list of ranges to be compressed
     * @return          the smallest working set, functionally identical to the input
     */
    private static List<List<Integer>> compressList(List<List<Integer>> rawList) {

        Integer low = rawList.get(0).get(0);
        Integer high = rawList.get(0).get(1);

        List<List<Integer>> compressedList = new ArrayList<>();

        for (List<Integer> range : rawList) {
            if (range.get(1) > high) { // if the range's upper value is higher than the know max...
                if (range.get(0) > high + 1) { // if the range's lower value is higher than the highest known value and not adjacent
                    compressedList.add(Arrays.asList(low, high)); // add new range to compressed list
                    low = range.get(0); // new high and low values
                    high = range.get(1);
                } else {
                    high = range.get(1); // set a new known max to combine ranges and continue
                }
            }
        }
        compressedList.add(Arrays.asList(low, high)); // add last range
        return compressedList;
    }

    /**
     * for use in a filter to remove range-pairs that could cause errors
     * @param rangeList a single range-pair
     * @return boolean true to remove, false to keep
     */
    private static boolean validateParameters(List<Integer> rangeList) {

        if (rangeList == null) return true;
        if (rangeList.isEmpty()) return true;
        if (rangeList.contains(null)) return true;
        if (rangeList.stream().anyMatch(x -> x > 99999)) return true;
        if (rangeList.stream().anyMatch(x -> x < 0)) return true;
        if (rangeList.size() != 2) return true;

        return false;
    }

    /**
     * Optional input preparation for making a straight integer list into a range-pair list
     * @param array a straight list of integers
     * @return a list of range-pairs
     */
    public static List<List<Integer>> makeRangePairList(List<Integer> array) {
        List<List<Integer>> list = new ArrayList<>();
        for (int index = 0; index < array.size(); index += 2) {
            list.add(Arrays.asList(array.get(index), array.get(index + 1)));
        }
        return list;
    }
}
