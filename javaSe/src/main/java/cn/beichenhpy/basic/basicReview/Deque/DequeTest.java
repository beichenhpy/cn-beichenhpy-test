package cn.beichenhpy.basic.basicReview.Deque;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote DequeTest description：双端队列测试
 * @since 2021/6/17 12:01
 */
public class DequeTest {
    /**
     * 括号匹配
     */
    boolean isValid(){
        String str = "[]";
        Deque<Character> integers = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (current == '[') {
                integers.addLast(current);
            }
            if (current == ']'){
                if (integers.peekLast() == Character.valueOf('[')) {
                    integers.pollLast();
                }else {
                    return false;
                }
            }
        }
        return integers.size() == 0;
    }



    @Test
    public void test1() {
        System.out.println(isValid() ? "匹配" : "不匹配");
    }
}
