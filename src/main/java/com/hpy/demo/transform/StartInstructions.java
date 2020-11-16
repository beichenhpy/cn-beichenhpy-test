package com.hpy.demo.transform;

/**
 * @ClassName StartInstructions
 * @Description TODO 指令启动类
 * @Author 韩鹏宇
 * @Date 2020/11/16 21:05
 * @Version 1.0
 */
public class StartInstructions{
    public static byte[] startOne(String str){
        return Instructions.getBytes(str);
    }
    public static byte[] startTwo(String str,int count){
        return Instructions.getBytes(str+Integer.toBinaryString(count));
    }
}
