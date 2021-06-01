package thread;
//本方法有线程安全问题
class Window extends Thread{
    private static int ticket = 10;
    public void run(){
        while(ticket>0){

                System.out.println(getName()+"卖票，票号为："+ticket);
                //上面不写Thread.currentThread().getName()的原因：该类继承于Thread，就默认了它是一个线程，此处省略了this
                ticket--;
        }

    }
}
public class WindowTest1 {
    public static void main(String[]args){
        Window w1 = new Window();
        Window w2 = new Window();
        Window w3 = new Window();
        w1.start();
        w2.start();
        w3.start();
    }
}
