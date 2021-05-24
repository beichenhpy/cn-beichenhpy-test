package cn.beichenhpy.basic.basicReview.Node;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote SingleNode description：
 * @since 2021/5/17 7:44 下午
 */

public class SingleNode<E> {
    private E element;
    private SingleNode<E> next;
    private int size;

    private SingleNode<E> head;
    private SingleNode<E> last;

    public SingleNode() {

    }

    public E getLast(){
        return last.element;
    }

    public E getFist(){
        return head.element;
    }

    private SingleNode(E element, SingleNode<E> next) {
        this.element = element;
        this.next = next;
    }

    public void linkLast(E element) {
        final SingleNode<E> l = last;
        final SingleNode<E> newNode = new SingleNode<>(element,null);
        last = newNode;
        if (l == null){
            head = newNode;
        }else {
            l.next = newNode;
        }
        size++;
    }


    public void linkFirst(E element) {
        final SingleNode<E> h = head;
        final SingleNode<E> newNode = new SingleNode<>(element,h);
        head = newNode;
        if (h == null){
            last = newNode;
        }
        size++;
    }


    public void removeLast() {

    }

    public static void main(String[] args) {
        final SingleNode<String> node = new SingleNode<>();
        node.linkLast("测试");
        node.linkLast("11");
        System.out.println(node.getLast());
        System.out.println(node.getFist());
        node.linkFirst("头");
        System.out.println(node.getFist());
        System.out.println(node.getLast());
    }
}
