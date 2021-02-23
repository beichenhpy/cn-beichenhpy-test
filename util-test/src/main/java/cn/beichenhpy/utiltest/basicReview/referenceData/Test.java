package cn.beichenhpy.utiltest.basicReview.referenceData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 引用数据类型修改是否需要返回值---不需要([String] 不属于一般的引用类型 是特殊的 需要返回值;装箱类如Integer也需要返回值)
 *               但是List等这些是引用数据类型，跟泛型无关 不需要返回值
 * @since 2021/2/8 16:00
 */
public class Test {
    /**
     * 测试List
     * @param list list
     */
    public void addList(List<String> list,List<Integer> integers){
        list.add("aaa");
        integers.add(1);
    }
    public void testList(){
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        addList(stringList,integerList);
        System.out.println(stringList);
        System.out.println(integerList);
    }

    public static void main(String[] args) {
        new Test().testList();
    }
}
