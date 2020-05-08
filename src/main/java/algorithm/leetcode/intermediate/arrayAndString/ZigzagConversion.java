package algorithm.leetcode.intermediate.arrayAndString;

/**
 * ### [6\. Z 字形变换](https://leetcode-cn.com/problems/zigzag-conversion/submissions/)
 * <p>
 * 难度 **中等**
 * <p>
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 比如输入字符串为 `"LEETCODEISHIRING"` 行数为 3 时，排列如下：
 * <p>
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如：`"LCIRETOESIIGEDHN"`。
 * <p>
 * 请你实现这个将字符串进行指定行数变换的函数：
 * <p>
 * *示例 1:**
 * <p>
 * *示例 2:**
 * <p>
 * ```
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * string convert(string s, int numRows);输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:
 * <p>
 * L     D     R
 * E   O E   I I
 * E C   I H   N
 * T     S     G```
 * <p>
 * #### Solution
 * <p>
 * Language: **Java**
 * <p>
 * ```java
 * class Solution {
 *    public String convert(String s, int numRows) {
 *        
 *    }
 * }
 * ```
 **/
public class ZigzagConversion {
    public static void main(String[] args) {
        String s = "123456789";
        String str = convert(s, 2);
        System.out.println(str);
    }

    public static String convert(String s, int numRows) {
        StringBuilder sb = new StringBuilder();
        if (numRows < 2 || s.length() < 2) {
            return s;
        }
        for (int i = 0; i < numRows; i++) {
            int posNext = 2 * (numRows - (i + 1));
            int antiNext = 2 * i;
            if (i == 0) {
                antiNext = posNext;
            }
            if (i == numRows - 1) {
                posNext = 2 * (numRows - 1);
                antiNext = posNext;
            }
            boolean pos = true;
            for (int j = i; j < s.length(); ) {
                sb.append(s.charAt(j));
                if (pos) {
                    j += posNext;
                } else {
                    j += antiNext;
                }
                pos = !pos;
            }
        }
        return sb.toString();
    }
}
