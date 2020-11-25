package com.hpy.demo.stream.Intermediate;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author A51398
 * @version 1.0
 * @description TODO
 * @since 2020/11/25 15:04
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<Integer>(){{add(1);add(2);add(3);add(3);add(3);add(3);add(3);add(3);add(3);}};
        /*
         * 求最大最小值
         * max()/min() param必须为 Comparable 接口的实现，利用lambada即可/方法引元
         * isPresent()判断是否有value
         * get() 来获取值
         */
        //3
        Integer max = integers.stream().distinct().max(Integer::compareTo).isPresent() ? integers.stream().distinct().max(Integer::compareTo).get() : -1;
        //1
        Integer min = integers.stream().distinct().min(Integer::compareTo).isPresent() ? integers.stream().distinct().min(Integer::compareTo).get() : -1;
        System.out.println(max);
        System.out.println(min);
        /*
         limit(count) param 保留多少个元素
         skip() param 跳过多少个
         filter(false) 条件为false的过滤掉 === 条件为true的需要
         */
        //只要5个元素 [1, 2, 3, 3, 3]
        System.out.println(Arrays.toString(integers.stream().limit(5).toArray()));
        //跳过前5个 [3, 3, 3, 3]
        System.out.println(Arrays.toString(integers.stream().skip(5).toArray()));
        //过滤掉等于3的，保留不等与3的 [1, 2]
        System.out.println(Arrays.toString(integers.stream().filter(integer -> integer != 3).toArray()));
        //转换值，可以用lambda或者方法引用 [成功, 失败, 无效值, 无效值, 无效值, 无效值, 无效值, 无效值, 无效值]
        System.out.println(Arrays.toString(integers.stream().map((Function<Integer, Object>) integer -> {
            if (integer == 1){
                return "成功";
            }else if(integer == 2){
                return "失败";
            }else {
                return "无效值";
            }
        }).toArray()));

    }
}
