package thread;
class Window11 extends Thread{
    private static int ticket=100;
    //Object obj = new Object();
    public void run(){
        while(true) {
            synchronized (Window11.class){
                if (ticket > 0) {
                    System.out.println(getName() + "卖票，票号为" + ticket);
                    ticket--;
                }
                else break;
            }
        }
    }
}
public class WindowTest11 {
    public static void main(String[]args){
        Window11 t1 = new Window11();
        Window11 t2= new Window11();
        Window11 t3 = new Window11();
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}
