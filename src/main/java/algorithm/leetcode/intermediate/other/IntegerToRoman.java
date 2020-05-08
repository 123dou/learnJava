package algorithm.leetcode.intermediate.other;



public class IntegerToRoman {
    private String _1 = "I";
    private String _4 = "IV";
    private String _5 = "V";
    private String _9 = "IX";
    private String _10 = "X";
    private String _40 = "XL";
    private String _50 = "L";
    private String _90 = "XC";
    private String _100 = "C";
    private String _400 = "CD";
    private String _500 = "D";
    private String _900 = "CM";
    private String _1000 = "M";

    public static void main(String[] args) {
        int num = 401;
        IntegerToRoman t = new IntegerToRoman();
        String s = t.intToRoman(num);
        System.out.println(s);
    }

    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        if (num <= 0) {
            return sb.toString();
        }
        for (; num > 0; ) {
            if (num >= 1000) {
                sb.append(_1000);
                num -= 1000;
            } else if (num >= 900) {
               sb.append(_900);
               num -= 900;
            } else if (num >= 500) {
                sb.append(_500);
                num -= 500;
            } else if (num >= 400) {
                sb.append(_400);
                num -= 400;
            } else if (num >= 100) {
                sb.append(_100);
                num -= 100;
            } else if (num >= 90) {
               sb.append(_90);
               num -= 90;
            } else if (num >= 50) {
               sb.append(_50);
               num -= 50;
            } else if (num >= 40) {
                sb.append(_40);
                num -= 40;
            } else if (num >= 10) {
                sb.append(_10);
                num -= 10;
            } else if (num >= 9) {
                sb.append(_9);
                num -= 9;
            } else if (num >= 5) {
                sb.append(_5);
                num -= 5;
            } else if (num >= 4) {
                sb.append(_4);
                num -= 4;
            } else {
                sb.append(_1);
                num -= 1;
            }
        }
        return sb.toString();
    }
}
