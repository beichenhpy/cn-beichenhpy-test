package com.hpy.demo.transform;

import java.util.Arrays;

/**
 * @author A51398
 * 二进制String-->16进制byte数组
 */
public class StringToByte {
    public static void main(String[] args) {
        String str1 = "000011110000000011110000";
        String str2 = "111111110000111100001111";
        String str3 = "0000000011110000";
        int parseInt = Integer.parseInt(str1, 2);
        int parseInt2 = Integer.parseInt(str2, 2);
        int parseInt3 = Integer.parseInt(str3, 2);
        byte[] bytes = new byte[8];
        int j =0;
        j = getJ(str1, parseInt, bytes, j);
        j = getJ(str2, parseInt2, bytes, j);
        getJ(str3, parseInt3, bytes, j);
        String str = "0000111100000000111100001111111100001111000011110000000011110000";
        System.out.println(Arrays.toString(bytes));
        System.out.println(Arrays.toString(getBytes(str)));
    }
    private static int getJ(String str, int parseInt, byte[] bytes, int j) {
        for (int i = 0; i < str.length() / 8; i++) {
            byte byte_i = (byte)((parseInt >> 8*((str.length()/8)-i-1)) & 0xff);
            bytes[j] = byte_i;
            j++;
        }
        return j;
    }
    /**
     * 转换算法
     * 传输过来的str为二进制8*8位
     * 需要将其按照每8位分割 分别计算
     * @param str 字符
     * @return 字节数组
     */
    private static byte[] getBytes(String str){
        // TODO 循环，每次处理8位
        int size = str.length()/8;
        //定义接收数组
        byte[] bytes = new byte[8];
        for (int i = 0; i < size; i++) {
            //每次截取8位计算
            String tmp = str.substring(8*i,8*(i+1));
            int tmpInt = Integer.parseInt(tmp,2);
            byte tmpByte = (byte)(tmpInt & 0xff);
            bytes[i] = tmpByte;
        }
        return bytes;
    }


}
