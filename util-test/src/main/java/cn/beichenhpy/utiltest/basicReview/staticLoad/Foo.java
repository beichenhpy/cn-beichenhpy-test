package cn.beichenhpy.utiltest.basicReview.staticLoad;

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
         * 【这里为什么执行static块呢？
         * 原理：类加载时会有类加载器收集所有的static方法去执行，所以无论是否实例化，都只执行一次类加载
         * 因此在实例化foo变量时，实际上是把实例初始化嵌入到了静态初始化流程中】
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
