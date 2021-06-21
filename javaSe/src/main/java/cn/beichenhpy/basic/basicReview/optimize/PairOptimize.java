package cn.beichenhpy.basic.basicReview.optimize;

import cn.hutool.core.lang.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote PairOptimize description：通过Pair优化if
 * @see Pair
 * @since 2021/6/21 19:00
 */
@Slf4j
public class PairOptimize {

    public static final String SUCCESS = "success";

    public static boolean originMethod(boolean show1, boolean show2, boolean show3, String progress1, String progress2, String progress3) {
        if (show1 && show2 && show3) {
            return SUCCESS.equals(progress1) && SUCCESS.equals(progress2) && SUCCESS.equals(progress3);
        } else if (show1 && show2) {
            return SUCCESS.equals(progress1) && SUCCESS.equals(progress2);
        } else if (show1 && show3) {
            return SUCCESS.equals(progress1) && SUCCESS.equals(progress3);
        } else if (show2 && show3) {
            return SUCCESS.equals(progress2) && SUCCESS.equals(progress3);
        } else {
            return false;
        }
    }

    /**
     * 通过先对show为false的remove 剩下的都是true对应的progress
     * @param conditions show+progress的组合
     * @param originSize 原始数组大小
     * @return true/false
     */
    public static boolean optimize(List<Pair<Boolean,String>> conditions,int originSize){
        //排除所有show为false的
        conditions.removeIf(booleanStringPair -> !booleanStringPair.getKey());
        int failShow = originSize - conditions.size();
        if (failShow > 1){
            return false;
        }
        //现在都是show为true的 直接返回对应的&&
        return conditions.stream()
                .map(pair -> SUCCESS.equals(pair.getValue()))
                .reduce(true, (a, b) -> a && b);
    }

    /**
     * 这种根据show的状态进行判断，只要有show==true,则会返回对应progress的state
     * @param conditions show+progress的组合
     * @return true/false
     */
    public static boolean optimizeAnother(List<Pair<Boolean,String>> conditions){
        return conditions.stream()
                .filter(Pair::getKey)
                .map(pair -> SUCCESS.equals(pair.getValue()))
                .reduce(true, (a, b) -> a && b);
    }

    public static void main(String[] args) {
        boolean test = originMethod(true, true, false, SUCCESS, SUCCESS, "fail");
        ArrayList<Pair<Boolean, String>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(true, SUCCESS));
        pairs.add(new Pair<>(true, SUCCESS));
        pairs.add(new Pair<>(false, "fail"));
        boolean test2 = optimize(pairs, 3);
        assert test == test2;
    }
}
