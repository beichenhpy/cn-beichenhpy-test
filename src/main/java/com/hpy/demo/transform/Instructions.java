package com.hpy.demo.transform;

import java.util.Arrays;

/**
 * 
 * @Description TODO 指令
 * @author 韩鹏宇
 * @since  2020/11/16 21:05
 * @version 1.0
 */
public class Instructions {
    /**
     * 将所有指令拼接为64位2进制数传入str
     * 转换算法
     * 传输过来的str为二进制8*8位
     * 需要将其按照每8位分割 分别计算
     *
     * @param str 字符
     * @return 字节数组
     */
    public static byte[] getBytes(String str) {
        // TODO 循环，每次处理8位
        int size = str.length() / 8;
        //定义接收数组
        byte[] bytes = new byte[8];
        for (int i = 0; i < size; i++) {
            //每次截取8位计算
            String tmp = str.substring(8 * i, 8 * (i + 1));
            int tmpInt = Integer.parseInt(tmp, 2);
            byte tmpByte = (byte) (tmpInt & 0xff);
            bytes[i] = tmpByte;
        }
        return bytes;
    }

    public static void main(String[] args) {
        byte[] cSrc = {0x00,0x28,0x47,0x65,0x18,(byte) 0xfe,(byte) 0xee, 0x00,0x00,0x40,0x02,0x08,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11};
        byte a = cSrc[0];
        for (int i = 0; i < cSrc.length-1; i++) {
            a ^= cSrc[i];
        }
        System.out.println(a);
    }
}
