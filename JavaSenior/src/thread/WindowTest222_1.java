package thread;
class Window222 implements Runnable{
    private int ticket = 100;
    public void run(){
        while(true) {
            show();
        }
    }
    public synchronized void show(){

            if(ticket>0) {
                System.out.println(Thread.currentThread().getName() + "卖票，票号为" + ticket);
                ticket--;
            }
    }
}
public class WindowTest222 {
    public static void main(String[]args){
        Window222 w = new Window222();
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
