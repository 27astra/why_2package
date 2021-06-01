package thread;
//也有线程安全问题
//出现安全问题的原因：当某个线程操作车票时，这个操作还没有完成时，其它线程也进来操作车票
class Window2 implements Runnable{
    private int ticket = 100;
    public void run(){
        while(true){
            if(ticket>0){
                System.out.println(Thread.currentThread().getName()+"卖票，票号为"+ticket);
                ticket--;
            }
            else break;
        }
    }
}
public class WindowTest2 {
    public static void main(String[]args){
        Window2 w = new Window2();
        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}
