package cn.beichenhpy.basic.basicReview.overloadOrOverride;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Son description：
 * @since 2021/6/9 20:46
 */
public class Son extends Father {

    /**
     * 重写 发生于子类和父类之间，方法签名和返回值一定要与父类方法相同
     *
     * @return String
     */
    @Override
    public String getInfo() {

        return "son";
    }

    /**
     * 重载
     *
     * @param age 发生于父子之间或者当前类，方法名一定相同，并且方法的形参一定不能重复，返回值任意均可
     */
    public void getInfo(int age) {
        System.out.println("getInfo重载-void返回值-年龄为：" + age);
    }
}
