# ZIPCOMPRESS

## Synopsis
ZipCompress takes ranges of zipcodes and reduces them by the lowest working set

## Table of Contents
> * [Title: ZipCompress](#ZIPCOMPRESS)
    >   * [Synopsis](#synopsis)
>   * [Table of Contents](#table-of-contents)
>   * [Code](#code)
      >      * [Extra Bits](#extra-bits)
>     * [Requirements](#requirements)
>     * [Build](#build)
>     * [Execute from Terminal](#execute-from-terminal)

>   * [License](#license)
>   * [About me](#about-me)

## Code

The workhorse of the code is the compress function. After being sorted the list can be traversed linearly.
```java
    private static List<List<Integer>> compressList(List<List<Integer>> rawList) {
```
The low and high values are set, and will be the values entered as a new range into the compressedList
```java
    Integer low = rawList.get(0).get(0);
    Integer high = rawList.get(0).get(1);

    List<List<Integer>> compressedList = new ArrayList<>();
```
The loop traversed the rawList, looking for ranges that exceed the current high value.

```java
   for (List<Integer> range : rawList) {
       if (range.get(1) > high) {
```

If the value of low is also outside the iterated range then we found a full range, and can add it to compressedList.

```java
if (range.get(0) > high + 1) { 
   compressedList.add(Arrays.asList(low, high));
   low = range.get(0);
   high = range.get(1);
```
Otherwise we change the high value and the potential next range is expanded.
```java
    } else {
        high = range.get(1); 
    }
```
Finally, after the list is fully traversed there will be a final range to add to compressedList
```java
    compressedList.add(Arrays.asList(low, high));
```

### Extra Bits
#### RangeComparator
A new comparator was built to sort lists of lists. It compares two range pairs by upper bounds and lower bounds. If the lower bounds are equal, then upper bounds are then compared.

```java
public class RangeComparator implements Comparator<List<Integer>> {
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
```
##### makeRangePairList
Method for optional input preparation. Used to making a straight integer list into a range-pair list
```java
public static List<List<Integer>> makeRangePairList(List<Integer> array) {
    List<List<Integer>> list = new ArrayList<>();
    for (int index = 0; index < array.size(); index += 2) {
        list.add(Arrays.asList(array.get(index), array.get(index + 1)));
    }
    return list;
}
```

### Requirements

Maven Version 3.6.3
Java 1.8

### Build

    mvn clean install

### Execute from terminal

    java -jar .\target\ZipCompress-1.0-SNAPSHOT.jar 94520 94598 94596 94602 

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

## About me

More information is available at [https://github.com/pmalolep/](https://github.com/pmalolep).