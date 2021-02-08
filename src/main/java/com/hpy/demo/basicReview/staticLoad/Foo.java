package com.hpy.demo.basicReview.staticLoad;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 类加载静态相关解析
 * @since 2021/2/8 15:30
 */
public class Foo {

    private static int count = 1;
    private static Foo foo = new Foo();
    static {
        System.out.println("执行静态块");
    }
    public Foo(){
        System.out.println("执行了构造函数");
        count++;
    }

    public static void main(String[] args) {
        /*
         * 这里的加载顺序
         * 调用Foo类--->Foo类需要初始化--->加载静态(static)修饰的语句 【static修饰的语句 执行按从上到下的顺序】只执行一次
         * -->从`private static int count`开始执行先加载到内存，然后赋予初值 0 然后赋值为 1
         * -->执行 `private static Foo foo` 先加载到内存 并赋予初值 null 然后赋值 new Foo()
         *  [我的理解：为什么不先执行static块，因为按顺序由上到下执行，当执行到第二行；
         *  对于类来说已经加载过了前两行static，所以不再加载；
         *  执行new Foo()的时候，也就只执行了构造函数]
         * -->执行 new Foo() 即 构造函数
         * -->执行 static {} 块
         */
        /*
         * output
         * 执行了构造函数
         * 执行静态块
         * 2
         * 执行了构造函数
         * 3
         */
        System.out.println(Foo.count);
        new Foo();
        System.out.println(Foo.count);
    }
}
