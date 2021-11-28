package edu.neu.coe.info6205.sort.chinese;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.CollatorHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.huskySortUtils.HuskyCoderFactory;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PureHuskyChineseSortTest {
    private final CollatorHelper<String> helper = new CollatorHelper<>("Chinese Helper");

    String[] input = new String[]{"刘持平", "洪文胜", "樊辉辉", "苏会敏", "高民政", "曹玉德", "袁继鹏",
            "舒冬梅", "杨腊香", "许凤山", "王广风", "黄锡鸿", "罗庆富", "顾芳芳", "宋雪光", "张三", "张四", "张安"};
    String[] expected = new String[]{"曹玉德", "樊辉辉", "高民政", "顾芳芳", "洪文胜", "黄锡鸿", "刘持平",
            "罗庆富", "舒冬梅", "宋雪光", "苏会敏", "王广风", "许凤山", "杨腊香", "袁继鹏", "张安", "张三", "张四"};

    @Test
    public void testSortString() {
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder, false, false);
        String[] result = sorter.sort(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testSort() {
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder, false, false);
        String[] chinese = new String[]{"张安安", "张安三", "张安安安","张安安三","张三","张四","张安"};
        String[] after = new String[]{"张安" ,"张安安", "张安安安" ,"张安安三", "张安三", "张三" ,"张四"};
        String[] result =sorter.sort(chinese);
        assertArrayEquals(after, result);
    }

    @Test
    public void sort() {
        int n = 200;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L);
        helper.init(n);
        String[] words = getWords("chinese-words-test.txt", PureHuskyChineseSortTest::lineAsList);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder, false, true);
        String[] result =sorter.sort(xs);
        assertEquals("曹玉德", result[0]);
        assertEquals("袁继鹏", result[160]);
    }

    @Test
    public void testWithInsertionSort() {
        int n = 200;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L);
        helper.init(n);
        String[] words = getWords("chinese-words-test.txt", PureHuskyChineseSortTest::lineAsList);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder, false, true);
        String[] result = sorter.sort(xs);
        assertEquals("曹玉德", result[0]);
        assertEquals("张四", result[199]);
    }

    /**
     * Create a string representing an integer, with commas to separate thousands.
     *
     * @param x the integer.
     * @return a String representing the number with commas.
     */
    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    /**
     * Method to open a resource relative to this class and from the corresponding File, get an array of Strings.
     *
     * @param resource           the URL of the resource containing the Strings required.
     * @param stringListFunction a function which takes a String and splits into a List of Strings.
     * @return an array of Strings.
     */
    static String[] getWords(final String resource, final Function<String, List<String>> stringListFunction) {
        try {
            final File file = new File(getPathname(resource, MSDChineseSortTest.class));
            final String[] result = getWordArray(file, stringListFunction, 2);
            System.out.println("getWords: testing with " + formatWhole(result.length) + " unique words: from " + file);
            return result;
        } catch (final FileNotFoundException e) {
            System.out.println("Cannot find resource: " + resource);
            return new String[0];
        }
    }

    private static List<String> getWordList(final FileReader fr, final Function<String,
            List<String>> stringListFunction, final int minLength) {
        final List<String> words = new ArrayList<>();
        for (final Object line : new BufferedReader(fr).lines().toArray())
            words.addAll(stringListFunction.apply((String) line));
        return words.stream().distinct().filter(s -> s.length() >= minLength).collect(Collectors.toList());
    }


    /**
     * Method to read given file and return a String[] of its content.
     *
     * @param file               the file to read.
     * @param stringListFunction a function which takes a String and splits into a List of Strings.
     * @param minLength          the minimum acceptable length for a word.
     * @return an array of Strings.
     */
    static String[] getWordArray(final File file, final Function<String, List<String>> stringListFunction,
                                 final int minLength) {
        try (final FileReader fr = new FileReader(file)) {
            return getWordList(fr, stringListFunction, minLength).toArray(new String[0]);
        } catch (final IOException e) {
            System.out.println("Cannot open file: " + file);
            return new String[0];
        }
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

    private static String getPathname(final String resource, @SuppressWarnings("SameParameterValue") final Class<?> clazz)
            throws FileNotFoundException {
        final URL url = clazz.getClassLoader().getResource(resource);
        if (url != null) return url.getPath();
        throw new FileNotFoundException(resource + " in " + clazz);
    }
}
