package cn.beichenhpy.basic.basicReview.compute;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Move description：
 * @since 2021/6/17 10:59
 */
public class Move {
    @Test
    public void test1() {
        int n = 3;
        //相当于除以 2^n
        int rightMove = 8 >> n;
        System.out.println(rightMove);
        // 相当于乘以 2^n
        int leftMove = 2 << n;
        System.out.println(leftMove);
    }

    @Test
    public void test2() {
        int left = 4;
        int right = 6;
        int result_or = left | right;
        int result_and = left & right;
        System.out.println(result_or);
        System.out.println(result_and);
    }
}
