package thread;

import static java.lang.Thread.sleep;

class Window22 implements Runnable{
    private int ticket = 100;
    public void run(){
        while (true) {
            synchronized(this) {
                if (ticket > 0) {
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖票，票号为" + ticket);
                    ticket--;
                } else break;
            }
        }
    }
}
public class WindowTest22 {
    public static void main(String[]args){
        Window22 w = new Window22();
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
