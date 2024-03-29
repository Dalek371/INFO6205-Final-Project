package edu.neu.coe.info6205.sort.chinese;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.util.GetWordsUtil;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MSDChineseSortTest {

    String[] input = new String[]{"刘持平", "洪文胜", "樊辉辉", "苏会敏", "高民政", "曹玉德", "袁继鹏",
            "舒冬梅", "杨腊香", "许凤山", "王广风", "黄锡鸿", "罗庆富", "顾芳芳", "宋雪光", "张三", "张四", "张安"};
    String[] expected = new String[]{"曹玉德", "樊辉辉", "高民政", "顾芳芳", "洪文胜", "黄锡鸿", "刘持平",
            "罗庆富", "舒冬梅", "宋雪光", "苏会敏", "王广风", "许凤山", "杨腊香", "袁继鹏", "张安", "张三", "张四"};

    @Test
    public void sort() {
        String[] res = MSDChineseSort.sort(input);
        assertArrayEquals(expected, res);
    }

    @Test
    public void sort1() {
        int n = 200;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L);
        helper.init(n);
        String[] words = GetWordsUtil.getWords("/chinese-words-test.txt", GetWordsUtil::lineAsList,
                MSDChineseSortTest.class);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        String[] res = MSDChineseSort.sort(xs);
        assertEquals("曹玉德", res[0]);
        assertEquals("张四", res[199]);
    }

    @Test
    public void sort2() {
        String[] words = GetWordsUtil.getWords("/shuffledChinese.txt", GetWordsUtil::lineAsList,
                MSDChineseSortTest.class);
        String[] res = MSDChineseSort.sort(words);
        String[] sorted = Arrays.copyOfRange(res, 0, 2000);
        GetWordsUtil.writeWords(sorted, String.join(File.separator, "src", "main", "resources",
                "sortedChineseWords-first-2000.txt"));
        assertEquals("阿安", res[0]);
    }

    @Test
    public void preProcessTest() {
        String[] res = MSDChineseSort.preProcess(input);
        assertArrayEquals(res, input);
    }
}
