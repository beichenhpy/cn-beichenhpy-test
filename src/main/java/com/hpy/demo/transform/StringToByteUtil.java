package com.hpy.demo.transform;

/**
 * @author A51398
 * 二进制String-->16进制byte数组
 * 根据业务 byte3-byte7需要传int转2进制string
 */
public class StringToByteUtil {
    /**
     * 将所有指令拼接为64位2进制数传入str
     * 转换算法
     * 传输过来的str为二进制8*8位
     * 需要将其按照每8位分割 分别计算
     *
     * @param str 字符
     * @return 字节数组
     */
    private static byte[] getBytes(String str) {
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





}
