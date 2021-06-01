package chess2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.io.*;

import static chess2.startFrame.*;

public class mainGame extends JFrame {
    private mainBoard mBoard;
    //初始化目前棋盘上被占用的位置的数组

     private final int [][]nowBoardArray;
    //会报错,因为此时的mBoard还没有进行初始化
    int type=-1;//模式： 1：人机对战开始 -1还没开始
    int person = 1;
    int max;//分值最大的点
    int score;//存放分数
//    JRadioButton preButton;选择人人或人机
//    JRadioButton nextButton;
    JButton begin_game;//开始游戏
    JButton regret;//悔棋
    JButton exit;//退出
    JButton musicbutton;//音效设置
    JButton restart;//重新开始
    ButtonGroup bgroup;//按钮组

    //无参构造函数
    public mainGame() {
        super("五子棋游戏");//标题
        setBounds(400, 40, 890, 750);//宽高位置
        this.setResizable(false);//设置此窗体是否可由用户调整大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
        this.setLayout(new BorderLayout());//设置布局
        ImageIcon icon = new ImageIcon("");
        setIconImage(icon.getImage());
        //导入棋盘对象
        mBoard = new mainBoard();
        //设置背景色
        Color background = Color.decode("#F7D99D");
        mBoard.setBackground(background);
        nowBoardArray=new int[mBoard.getBoard_size()][mBoard.getBoard_size()];

        //添加GUI界面的一些相关组件
        JPanel j1=new JPanel();
        begin_game=new JButton("开始游戏");
        regret=new JButton("悔棋");
        exit=new JButton("返回主界面");
        musicbutton=new JButton("音效设置");
        restart=new JButton("重新开始");
        //设置按钮字体大小为加粗，16号字
        Font Fonts = new Font("幼圆", Font.BOLD, 16);
        j1.add(begin_game);
        j1.add(regret);
        j1.add(exit);
        j1.add(musicbutton);
        j1.add(restart);
        GridLayout gridlayout=new  GridLayout(5,1,0,80);
        gridlayout.setVgap(70);//设置这个布局的内部元素的垂直间距
        j1.setLayout(gridlayout);

        //字体设置和按钮颜色设置
        begin_game.setFont(Fonts);
        regret.setFont(Fonts);
        exit.setFont(Fonts);
        musicbutton.setFont(Fonts);
        restart.setFont(Fonts);
        begin_game.setBackground(Color.white);
        regret.setBackground(Color.white);
        exit.setBackground(Color.white);
        musicbutton.setBackground(Color.white);
        restart.setBackground(Color.white);

        begin_game.setIcon(new ImageIcon("/chess2/images/start.png"));
        regret.setIcon(new ImageIcon("/chess2/images/regret.png"));
        exit.setIcon(new ImageIcon("/chess2/images/exit.png"));
        musicbutton.setIcon(new ImageIcon("/chess2/images/music.png"));
        restart.setIcon(new ImageIcon("/chess2/images/restart.png"));

        begin_game.setPreferredSize(new Dimension(110,40));
        j1.setBackground(background);
        j1.setBorder(new EmptyBorder(70,5,70,30));//填充JPanel边距
        this.add(j1,BorderLayout.EAST);


        this.add(mBoard,BorderLayout.CENTER);
        setVisible(true);


        //给棋盘添加监听鼠标落子事件,匿名内部适配器类
        mBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(type==-1){
                    JOptionPane.showMessageDialog(null,"请点击右上角的按钮开始游戏！");
                }
                else{
                    super.mouseClicked(e);
                    play(e);
                }

            }
        });
//        退出
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int select = JOptionPane.showConfirmDialog(null, "真的要返回主界面吗？若返回，该局将不会保存。", "提示", JOptionPane.YES_NO_OPTION);

                // select 为用户点的第几个按钮,yes为0，no为1
                if(select == 0)
                {
                   new startFrame();
                    setVisible(false);
                }
            }
        });

//        开始游戏
        begin_game.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    if(type==-1){
                        JOptionPane.showMessageDialog(null,"游戏开始！");
                        super.mouseClicked(e);
                        type = 1;
                        begin_game.setEnabled(false);
                    }

                }
        });

//        悔棋
        regret.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                如果没有棋子不能悔棋
                if(mBoard.pieceList.size()==0){
                    JOptionPane.showMessageDialog(null,"当前棋盘无棋子，不能悔棋。");
                    return;
                }
//
//                int colortype=
                int x1= mBoard.pieceList.get(mBoard.pieceList.size()-1).getX();
                int y1= mBoard.pieceList.get(mBoard.pieceList.size()-1).getY();
                System.out.print("x1="+x1);
                System.out.println("y1="+y1);

                int x2= mBoard.pieceList.get(mBoard.pieceList.size()-2).getX();
                int y2= mBoard.pieceList.get(mBoard.pieceList.size()-2).getY();
                System.out.print("x2="+x2);
                System.out.println("y2="+y2);

//               int x3= mBoard.pieceList.get(mBoard.pieceList.size()-3).getX();
//                int y3= mBoard.pieceList.get(mBoard.pieceList.size()-3).getY();
//                int type=mBoard.pieceList.get(mBoard.pieceList.size()-3).getType();
//                System.out.println("mBoard.pieceList.size()-1="+(mBoard.pieceList.size()-1));
                    mBoard.pieceList.remove(mBoard.pieceList.size()-1);
//                System.out.println("mBoard.pieceList.size()-1="+(mBoard.pieceList.size()-1));
                    nowBoardArray[y1][x1]=0;
                    mBoard.pieceList.remove(mBoard.pieceList.size()-1);
                    nowBoardArray[y2][x2]=0;
//                System.out.println(mBoard.pieceList.get(mBoard.pieceList.size()-1).getType()+"这是哈哈哈哈哈哈哈");


//                    nowBoardArray[x1][y1]=null;
//                mBoard.pieceList.get(mBoard.pieceList.size()-1).setX(x);
//                mBoard.pieceList.get(mBoard.pieceList.size()-1).setX(y);
//                nowBoardArray[x3][y3]=type;
                    if(mBoard.pieceList.size()>=0){

                        mBoard.repaint();
                    }
//                    setVisible(false);
            }
        });

        //重新开始
        restart.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int select = JOptionPane.showConfirmDialog(null, "确定重新开始吗？当前局将不会被保存哦。", "提示", JOptionPane.YES_NO_OPTION);

                // select 为用户点的第几个按钮,yes为0，no为1
                if(select == 0)
                {
                    int size = mBoard.pieceList.size();
                    while(size>0){
                        size--;
                        int x1= mBoard.pieceList.get(mBoard.pieceList.size()-1).getX();
                        int y1= mBoard.pieceList.get(mBoard.pieceList.size()-1).getY();
                        nowBoardArray[y1][x1]=0;
                        mBoard.pieceList.remove(mBoard.pieceList.size()-1);
                    }
                    mBoard.repaint();
                    type=-1;
                    begin_game.setEnabled(true);
                }
            }
        });
        //音效设置
        musicbutton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int select,flag=0;
                if(musicflag==0){
                    select = JOptionPane.showConfirmDialog(null, "当前无音乐，确认开启吗？", "提示", JOptionPane.YES_NO_OPTION);

                }
               else {
                    select = JOptionPane.showConfirmDialog(null, "当前正在播放音乐，确认关闭吗？", "提示", JOptionPane.YES_NO_OPTION);
                    flag=1;
                }
                // select 为用户点的第几个按钮,yes为0，no为1
                if (flag == 0) {

                    if(select == 0)
                    {
                        music.loop();
                        musicflag =1;
                        musicflag1 =0;
                    }
                    else if(select==1){

                    }
                }
                else if (flag == 1) {

                    if(select == 0)
                    {
                        music.stop();
                        musicflag =0;
                        musicflag1=1;
                    }
                    else if(select==1){

                    }
                }

            }
        });
    }


    /*将四个方向转换成字符串
    //将横方向转换
//    对x,y坐标落子时，横就是这一行，有棋子并且type相同的为1,type不同的是2，没有棋子是0*/
    public String getString1(int[] arr){
        String s="";
        for(int i=0;i<arr.length;i++){
            s+=arr[i];
        }
        return s;
//        一整行返回
    }


    //将竖方向转换
    public String getString2(int y){
        String s="";
        for(int i=0;i<nowBoardArray.length;i++){
            s+=nowBoardArray[i][y];
        }
        return s;
//        将落子y坐标这一列返回
    }

    //撇方向
    public String getString3(int x,int y){
        String s="";
        if(x<=y){
            int gap=y-x;
            for(int i=0;i+gap<=18;i++) s+=nowBoardArray[i][i+gap];
        }else{
            int gap=x-y;
            for(int i=0;i+gap<=18;i++) s+=nowBoardArray[gap+i][i];
        }
//        如果x<y那么从x=0一直取下来，如果x>y那么久从y=0一直取下来，返回这一撇
        return s;
    }


    //将捺方向转换成字符串
    public String getString4(int x,int y){
        String s="";
        //18即nowBoardArray的长度-1
        if(x+y<=18){
            int gap=x+y;
            for(int i=gap;i>=0;i--) s+=nowBoardArray[i][gap-i];
            //从下往上找的一开始x更大
        }
        else{
            //如果x+y比18更大的话需要从x=18最后一行开始向上找
            int gap=x+y;
            for(int i=18;gap-i<=18;i--) s+=nowBoardArray[i][gap-i];
        }
        return s;
    }


    //返回各个方向属于哪种棋型
    public int getChessType(String s,int x,int type){

        //十种棋型
        String[][] matchS1={{"11111"},{"011110"},{"011112","211110","0101110","0111010","0110110"},{"01110","010110","011010"},
                {"001112","211100","010112","211010","011012","210110","10011","11001","10101","2011102"},
                {"00110","01100","01010","010010"},
                {"000112","211000","001012","210100","010012","210010","10001","2010102","2011002","2001102"},
                {"211112"},
                {"21112"},
                {"2112"}};

        String[][] matchS2={{"22222"},{"022220"},{"022221","122220","0202220","0222020","0220220"},{"02220","020220","022020"},
                {"002221","122200","020221","122020","022021","120220","20022","22002","20202","1022201"},
                {"00220","02200","02020","020020"},
                {"000221","122000","002021","120200","020021","120020","20002","1020201","1022001","1002201"},
                {"122221"},
                {"12221"},
                {"1221"}};
        String[][] matchS;
        if(type==1) matchS=matchS1;
        else
            matchS=matchS2;

        int i,j;
        //遍历二维数组
        for(i=0;i<matchS.length;i++){
            for(j=0;j<matchS[i].length;j++){
                int index=0;
//                public int indexOf(int ch, int fromIndex):
////                返回从 fromIndex 位置开始查找指定字符在字符串中第一次出现处的索引，
////                如果此字符串中没有这样的字符，则返回 -1。
                //查找待查找数组在棋型数组中的一维位置即几号棋型
                while((index=s.indexOf(matchS[i][j],index))!=-1){
                    if(x>=index&&x<index+matchS[i][j].length())//即此时要下的棋子正好是找到棋型的元素的其中一个棋子
                        return i;//返回是第几种棋型
                    index+=matchS[i][j].length()-1;
                }

            }
        }
        return 10;//没找到对应棋型返回不存在的数组下标
    }


    //获取当前落子的得分情况
    public int getScore(int x,int y,int type){
        nowBoardArray[x][y]=type;
        //将现在已经下了的棋子都设置一下到底是黑子还是白子
        //分别将四个方向的数组转换成对应的字符串，再获取四个方向的棋型
        //横
        String s1=getString1(nowBoardArray[x]);
        int chessType1=getChessType(s1,y,type);
        //竖
        String s2=getString2(y);
        int chessType2=getChessType(s2,x,type);
        //撇
        String s3=getString3(x,y);
        int chessType3=getChessType(s3,y>=x?x:y,type);
        //捺
        String s4=getString4(x,y);
        int a=y;
        if(x+y>18) a=18-x;
        int chessType4=getChessType(s4,a,type);

        int cnt[]=new int[11];
        for(int i=0;i<11;i++) cnt[i]=0;//从0-9种棋型的数目,10表示没有符合的棋型
        cnt[chessType1]++;
        cnt[chessType2]++;
        cnt[chessType3]++;
        cnt[chessType4]++;
        //将四个方向对应的棋型对应的数组位置记录好

        if(chessType1==10&&chessType2==10&&chessType3==10&&chessType4==10) return 0;
        if(cnt[0]>=1){//长连
            if(type==1) return 90000;
            return 100000;
        }

        if(cnt[1]>=1||(cnt[2]>=2)||(cnt[2]>=1&&cnt[3]>=1)){
            if(type==1) return 9000;
            return 10000;
        }

        if(cnt[3]>=2){
            if(type==1) return 4500;
            return 5000;
        }
        if(cnt[3]>=1&&cnt[4]>=1){
            if(type==1) return 900;
            return 1000;
        }
        if(cnt[2]>=1){
            if(type==1) return 450;
            return 200;
        }

        if(cnt[3]>=1){
            if(type==1) return 180;
            return 200;
        }
        if(cnt[5]>=2){
            if(type==1) return 90;
            return 100;
        }

        if(cnt[4]>=1) return 50;
        if(cnt[5]>=1&&cnt[6]>=1) return 10;
        if(cnt[5]>=1) return 5;
        if(cnt[6]>=1) return 3;
        if(cnt[7]>=1) return -5;
        if(cnt[8]>=1) return -5;
        if(cnt[9]>=1) return -5;
        return 0;
    }



    //判断某方是否赢了
    public boolean ifWin(int x,int y,int type){
        //传入1表示判断人的棋子是不是长连，2表示机器的
        //在该棋子的四个方向的字符串中找11111，找到了即已经赢了
        String tempStr="";
        if(type==1) tempStr="11111";
        else tempStr="22222";
        //横竖撇捺四个方向
        String s1=getString1(nowBoardArray[x]);
        if(s1.indexOf(tempStr)!=-1) return true;
        String s2=getString2(y);
        if(s2.indexOf(tempStr)!=-1) return true;
        String s3=getString3(x,y);
        if(s3.indexOf(tempStr)!=-1) return true;
        String s4=getString4(x,y);
        if(s4.indexOf(tempStr)!=-1) return true;
        return false;
    }

    //记录该棋子最大的得分点
//    参数是：扫描到的机器可以落子的即以人落子为中心的四个方向的地方
    public int fun(int x,int y){
        //坐标没有越界并且人没有在这个坐标落子,
        if(x>=0&&x<19&&y>=0&&y<19&&nowBoardArray[x][y]==0){
            int min= x<=y?x:y;
            if(x+y>=18) min= x>=y? 18-x:18-y;
            int a=getScore(x,y,2);//这个子放进去之后形成的棋型的分数
            int b=getScore(x,y,1);//人的分数
            int end=a+b+min;//选择人和机器分数之和返回
            if(end>max){
                System.out.println("进攻："+a);
                System.out.println("防守："+b);
                System.out.println("总计："+end);
            }
            nowBoardArray[x][y]=0;
            return end;
        }
        return 0;
    }

    //结束算法
    public void end(){
        for(int []x:nowBoardArray){
            Arrays.fill(x,0);
        }
        person=1;
        mBoard.clear();
        type=-1;
        begin_game.setEnabled(true);

    }

    //电脑落子
    public void ITnext(){
        max = 0;
        //最大的分点
        int nextX=0,nextY=0;
        int[][] gameArr2 = new int[19][19];//机器的棋子数组
        for(int i = 0;i < gameArr2.length;i++){
            gameArr2[i] = nowBoardArray[i].clone();
        }
        int[] xx={-2,-1,0,1,2};
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                if(nowBoardArray[i][j]!=0){
                    //如果这个坐标有人下的棋子，以这个棋子为中心扫描四个方向
                    for(int a=0;a<xx.length;a++){
                        for(int b=0;b<xx.length;b++){
                            if(ifBorder(i+xx[a],j+xx[b])) continue;//没有超过界限则继续
                            if(gameArr2[i+xx[a]][j+xx[b]]==0){//如果机器也没有在这个坐标落子
                                gameArr2[i+xx[a]][j+xx[b]] = fun(i+xx[a],j+xx[b]);//此时落下的地方是人和机器分现有的分数之和
                                if(gameArr2[i+xx[a]][j+xx[b]]>max){
                                    max=gameArr2[i+xx[a]][j+xx[b]];
                                    nextX = i+xx[a];
                                    nextY = j+xx[b];//找分值最大的下一个落子点
//                                    System.out.println("哈哈哈");
                                }
                            }
                        }
                    }
                }
            }
        }
//        System.out.println("enenenen");
        nowBoardArray[nextX][nextY] = 2;

        //四个方向扫描得分最高的进行落子
        mBoard.addPieces(new chessPiece(nextY,nextX,2));
        if(ifWin(nextX,nextY,2)) {
            JOptionPane.showMessageDialog(null,"机器赢了哦！","tip",JOptionPane.PLAIN_MESSAGE);
            end();
        }
//        else if(ifWin(nextX,nextY,1)){
//            JOptionPane.showMessageDialog(null,"你赢了哦！","tip",JOptionPane.PLAIN_MESSAGE);
//            end();
//        }
//        else{
//            JOptionPane.showMessageDialog(null,"出现错误","tip",JOptionPane.PLAIN_MESSAGE);
//            end();
//        }
    }



    //处理鼠标点击事件的方法,对棋子的落点进行处理
    private void play(MouseEvent e) {
        if(type!=-1){
            //若游戏开始
            int length=mBoard.getLength();//格子的边长
            //计算出鼠标点击事件相对于棋盘的位置
            System.out.println("getX="+e.getX());
            System.out.println("gety="+e.getY());
            double x1=(double)(e.getX()-mBoard.margin)/length;
            double y1=(double)(e.getY()-mBoard.margin)/length;
            System.out.println("x1="+x1);
            System.out.println("y1="+y1);
            int x2=(int)Math.rint(x1);
            int y2=(int)Math.rint(y1);
            System.out.println("x2="+x2);
            System.out.println("y2="+y2);
            //给棋盘添加棋子并且传入棋子的相对于棋盘的坐标
            /**
             * 落子需要的条件：
             * 1、该位置没有其他棋子
             * 2、不能超过棋盘的边界区域
             */
            System.out.println("你的棋子坐标："+y2+" "+x2);//要反转一下x和y

           if(x2>=0&&y2>=0&&x2<mBoard.getBoard_size()&&y2<mBoard.getBoard_size()&&nowBoardArray[y2][x2]==0){
               nowBoardArray[y2][x2]=person;//person一直是1，因为调用鼠标点击事件只能是人自己调用,被占据的为1
               mBoard.addPieces(new chessPiece(x2,y2,1));
               //添加完棋子之后需要将棋盘上二维数组的对应位置占据
               if(!ifWin(y2,x2,1))
               ITnext();
               else{
                   System.out.println("你赢了");
                   char[]ca ;//定义存放字符流的数
                   ca =readScore();
                   addScore(ca);JOptionPane.showMessageDialog(null,"你赢了哦！","tip",JOptionPane.PLAIN_MESSAGE);
                   end();
               }

           }

        }
    }

    // 判断是否越界
    public boolean ifBorder(int x,int y){
        if(x<0||x>=19||y<0||y>=19) return true;
        else return false;
    }
//读取分数
   private char[] readScore() {
        File inF = new File("D:\\javadesign3\\workplace_IDEA1\\src\\chess2\\images\\score.txt");
       // File outF = new File("D:\\developer_tools\\IDEA\\workplace_IDEA1\\src\\chess2\\images\\score2.txt");
        char[]ca = new char[(int)inF.length()];//定义存放字符流的数组

        //length：返回文件的长度，但返回值时long型，需强转成int型
        try {
            FileReader in = new FileReader(inF);
            //FileWriter out = new FileWriter(outF);
           // System.out.println("开始文件复制");
            in.read(ca);//将整个字符流读入数组ca中
            System.out.println(ca);
            //out.write(ca);
            in.close();//out.close();
            //System.out.println("结束文件复制");
        }catch(FileNotFoundException e) {
            System.out.println("文件不存在"+e);
        }catch(IOException e) {
            System.out.println("文件读取错误");
        }
        return ca;
    }

    //添加分数
    private   void addScore(char[] ca){

        try {
            //File inF = new File("D:\\developer_tools\\IDEA\\workplace_IDEA1\\src\\chess2\\images\\score.txt");
            File outF = new File("D:\\javadesign3\\workplace_IDEA1\\src\\chess2\\images\\score.txt");
            //System.out.println("inF.length="+inF.length());
            //char[]ca = new char[(int)inF.length()];//定义存放字符流的数组
            //length：返回文件的长度，但返回值时long型，需强转成int型
            //FileReader in = new FileReader(inF);
            FileWriter out = new FileWriter(outF);
           // System.out.println("开始文件复制");
            //in.read(ca);//将整个字符流读入数组ca中
            String str = String.valueOf(ca);
            int a = Integer.parseInt(str);
            a++;
            str = ""+a;
            ca = str.toCharArray();
            System.out.println(ca);
            out.write(ca);
            out.close();
            //System.out.println("结束文件复制");
        }catch(FileNotFoundException e) {
            System.out.println("文件不存在"+e);
        }catch(IOException e) {
            System.out.println("文件读取错误");
        }

    }
    public static void main(String[] args) {

        mainGame m1=new mainGame();

    }
}
