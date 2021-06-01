package thread;
//此代码有死锁问题
//例：先执行线程一，进入用s1为锁的同步代码块中，sleep阻塞，此时线程二很有可能执行，则进入以s2为锁的同步代码块中，sleep阻塞，线程一等着s2，线程二等着s1
public class DeadThreadTest {
    public static void main(String[]args){
        StringBuffer s1 = new StringBuffer();//同步资源
        StringBuffer s2 = new StringBuffer();//同步资源
        new Thread(){//线程一
            public void run(){
                synchronized (s1){
                    s1.append("a");
                    s2.append("1");
                    try {
                        Thread.sleep(100);//加sleep，是为了增大死锁概率
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (s2){
                        s1.append("b");
                        s2.append("2");
                    }
                    System.out.println(s1);
                    System.out.println(s2);
                }

            }
        }.start();
        new Thread(new Runnable(){//线程二
            public void run(){
                synchronized (s2){
                    s1.append("c");
                    s2.append("3");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (s1){
                        s1.append("d");
                        s2.append("4");
                    }
                    System.out.println(s1);
                    System.out.println(s2);
                }

            }
        }).start();
    }
}
