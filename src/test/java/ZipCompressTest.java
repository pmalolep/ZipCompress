import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZipCompressTest {

    @Test
    public void firstTest() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(94133, 94133, 94200, 94299, 94600, 94699));
        String expected = "[[94133, 94133], [94200, 94299], [94600, 94699]]";
        List<List<Integer>> result = ZipCompress.process(testList);

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void secondTest() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(94133, 94133, 94200, 94299, 94226, 94399));
        String expected = "[[94133, 94133], [94200, 94399]]";
        List<List<Integer>> result = ZipCompress.process(testList);

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void getTwoRangesFromFive() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(20000, 30000, 10000, 20000, 60000, 70000, 10000, 50000, 70000, 90000));
        String expected = "[[10000, 50000], [60000, 90000]]";
        List<List<Integer>> result = ZipCompress.process(testList);

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void nullInput() {
        String expected = "[]";
        List<List<Integer>> result = ZipCompress.process(null);

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void reversedRange() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(95000, 65000, 64000, 30000, 40000, 25000));
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[25000, 64000], [65000, 95000]]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void singleValueRange() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(25000, 25000));
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[25000, 25000]]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void compressToOneRange() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(15000, 95000, 25000, 35000, 45000, 55000, 65000, 75000));
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[15000, 95000]]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void nullInRange() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(15000, 25000, 25000, 35000, 45000, null, 65000, 75000));
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[15000, 35000], [65000, 75000]]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void rangeIsNull() {
        List<List<Integer>> testList = new ArrayList<>();
        testList.add(Arrays.asList(15000, 25000));
        testList.add(null);
        testList.add(Arrays.asList(25000, 35000));
        testList.add(Arrays.asList(35000, 45000));
        testList.add(Arrays.asList(65000, 75000));

        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[15000, 45000], [65000, 75000]]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void wrongSizedRange() {
        List<List<Integer>> testList = new ArrayList<>();
        testList.add(Arrays.asList(15000, 25000));
        testList.add(Arrays.asList(25000, 35000));
        testList.add(Arrays.asList(35000, 45000));
        testList.add(Arrays.asList(65000, 75000));
        testList.add(Arrays.asList(85000));
        testList.add(Arrays.asList());

        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[15000, 45000], [65000, 75000]]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void onlyOneEmptyRange() {
        List<List<Integer>> testList = new ArrayList<>();
        testList.add(Collections.emptyList());
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void emptyList() {
        List<List<Integer>> testList = Collections.emptyList();
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void smallRanges() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(0, 0, 1, 1, 2, 2, 3, 3));
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[0, 3]]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void smallRangesPartDuex() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(0, 0, 1, 1, 2, 2, 3, 3, 5, 7));
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[0, 3], [5, 7]]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void outOfBoundsRanges() {
        List<List<Integer>> testList = makeListofLists(Arrays.asList(-1,0,99999,1000000));
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[]";

        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void allAmericanRanges() {
        List<List<Integer>> testList = makeListofLists(
                Arrays.asList(
                        99501, 99950, 35004, 36925,
                        71601, 72959, 75502, 75502,
                        85001, 86556, 90001, 96162,
                        80001, 81658, 6001, 6389,
                        6401, 6928, 20001, 20039,
                        20042, 20599, 20799, 20799,
                        19701, 19980, 32004, 34997,
                        30001, 31999, 39901, 39901,
                        96701, 96898, 50001, 52809,
                        68119, 68120, 83201, 83876,
                        60001, 62999, 46001, 47997,
                        66002, 67954, 40003, 42788,
                        70001, 71232, 71234, 71497,
                        1001, 2791, 5501, 5544,
                        20331, 20331, 20335, 20797,
                        20812, 21930, 3901, 4992,
                        48001, 49971, 55001, 56763,
                        63001, 65899, 38601, 39776,
                        71233, 71233, 59001, 59937,
                        27006, 28909, 58001, 58856,
                        68001, 68118, 68122, 69367,
                        3031, 3897, 7001, 8989,
                        87001, 88441, 88901, 89883,
                        6390, 6390, 10001, 14975,
                        43001, 45999, 73001, 73199,
                        73401, 74966, 97001, 97920,
                        15001, 19640, 0, 0,
                        2801, 2940, 29001, 29948,
                        57001, 57799, 37010, 38589,
                        73301, 73301, 75001, 75501,
                        75503, 79999, 88510, 88589,
                        84001, 84784, 20040, 20041,
                        20040, 20167, 20042, 20042,
                        22001, 24658, 5001, 5495,
                        5601, 5907, 98001, 99403,
                        53001, 54990, 24701, 26886,
                        82001, 83128));
        List<List<Integer>> result = ZipCompress.process(testList);
        String expected = "[[0, 0], [1001, 2791], [2801, 2940], [3031, 3897], [3901, 4992], [5001, 5495], [5501, 5544], [5601, 5907], [6001, 6390], [6401, 6928], [7001, 8989], [10001, 14975], [15001, 19640], [19701, 19980], [20001, 20797], [20799, 20799], [20812, 21930], [22001, 24658], [24701, 26886], [27006, 28909], [29001, 29948], [30001, 31999], [32004, 34997], [35004, 36925], [37010, 38589], [38601, 39776], [39901, 39901], [40003, 42788], [43001, 45999], [46001, 47997], [48001, 49971], [50001, 52809], [53001, 54990], [55001, 56763], [57001, 57799], [58001, 58856], [59001, 59937], [60001, 62999], [63001, 65899], [66002, 67954], [68001, 68120], [68122, 69367], [70001, 71497], [71601, 72959], [73001, 73199], [73301, 73301], [73401, 74966], [75001, 79999], [80001, 81658], [82001, 83128], [83201, 83876], [84001, 84784], [85001, 86556], [87001, 88441], [88510, 88589], [88901, 89883], [90001, 96162], [96701, 96898], [97001, 97920], [98001, 99403], [99501, 99950]]";

        Assert.assertEquals(expected, result.toString());
    }

    // makeListofLists is only to make reading and building tests easier
    private List<List<Integer>> makeListofLists(List<Integer> arr) {
        List<List<Integer>> list = new ArrayList<>();
        for (int index = 0; index < arr.size(); index += 2) {
            list.add(Arrays.asList(arr.get(index), arr.get(index + 1)));
        }
        return list;
    }

}