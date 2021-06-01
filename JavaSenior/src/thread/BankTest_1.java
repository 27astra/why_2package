package thread;
//解决懒汉式的线程安全问题
class Bank{
    private Bank(){

    }
    private static Bank instance =null;
    public Bank getInstance(){
        //方式一：
        //synchronized(Bank.class){
           //if(instance==null)
                //instance = new Bank();
            //return instance;
        //}
        //方式二：效率更高
        // 线程123先同时进来，23被挡在同步代码块外···1创建成功，23还要再分别进同步代码块判断，此时效率是比较低的
        //但之后线程45进来，就直接被第一个if语句跳过同步代码块直接来到return，大家再也不用都要在同步代码块外面排队了
        if(instance==null){
            synchronized(Bank.class) {
                if(instance==null) {
                    instance = new Bank();
                }
            }
        }
        return instance;
    }
}
public class BankTest {
}
