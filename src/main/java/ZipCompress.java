
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

public class ZipCompress {

    public static void main(String[] args) {

        List<Integer> intList = Arrays.stream(args).map(Integer::parseInt).collect(Collectors.toList());
        List<List<Integer>> preparedList = new ArrayList<>();

        if( intList.size()%2!=0) intList.add(null);
        for (int index = 0; index < intList.size(); index += 2) {
            preparedList.add(Arrays.asList(intList.get(index), intList.get(index + 1)));
        }

        System.out.println(process(preparedList).toString());
    }

    public static List<List<Integer>> process(List<List<Integer>> zipList) {

        if (zipList == null) return emptyList();
        zipList.removeIf(ZipCompress::validateParameters);
        if (zipList.isEmpty()) return emptyList();

        zipList.forEach(Collections::sort); // puts each range pair in proper (low, high) order
        zipList.sort(new RangeComparator());

        return compressList(zipList);
    }

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

    // any true values cause that range pair to be removed from list
    private static boolean validateParameters(List<Integer> rangeList) {

        if (rangeList == null) return true;
        if (rangeList.isEmpty()) return true;
        if (rangeList.contains(null)) return true;
        if (rangeList.stream().anyMatch(x -> x > 99999)) return true;
        if (rangeList.stream().anyMatch(x -> x < 0)) return true;
        if (rangeList.size() != 2) return true;
        ;
        return false;
    }
}
