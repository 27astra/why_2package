package thread;
class Window111 extends Thread{
    private static int ticket = 100;
    public void run(){
        while(true) {//此时用Test1中的ticket>0会出错的，因为>0相当于对共享资源操作，但while不能被锁住，要不然一个线程进来了就一直执行while循环，相当于单线程
            show();
        }

    }
    public static synchronized  void show() {//要成为静态方法：非静态的锁是this，即有三个锁了
        if (ticket > 0) {
            try {
               sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "卖票，票号为：" + ticket);
            ticket--;
        }
    }
}
public class WindowTest111 {
    public static void main(String[]args){
        Window111 w1 = new Window111();
        Window111 w2 = new Window111();
        Window111 w3 = new Window111();
        w1.setName("窗口1");
        w2.setName("窗口2");
        w3.setName("窗口3");
        w1.start();
        w2.start();
        w3.start();
    }
}

