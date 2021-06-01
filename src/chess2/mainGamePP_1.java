package chess2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import static chess2.startFrame.*;

public class mainGamePP extends JFrame {
    private chess2.mainBoard mBoard;
    //初始化目前棋盘上被占用的位置的数组

    private final int [][]nowBoardArray;
    //会报错,因为此时的mBoard还没有进行初始化
    int type=-1;//模式： 1：人机对战开始 -1还没开始
    //int person = 1;
   // int max;//分值最大的点
    int piecetype = 1;//棋子的类型，1为黑子，2为白子
    //    JRadioButton preButton;选择人人或人机
//    JRadioButton nextButton;
    JButton begin_game;//开始游戏
    JButton regret;//悔棋
    JButton exit;//退出
    JButton musicbutton;//音效设置
    JButton restart;//重新开始

    ButtonGroup bgroup;//按钮组

    //无参构造函数
    public mainGamePP() {
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
                    if(piecetype==1){
                        play1(e);
                    }
                    else{
                        play2(e);
                    }
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

//               int x3= mBoard.pieceList.get(mBoard.pieceList.size()-3).getX();
//                int y3= mBoard.pieceList.get(mBoard.pieceList.size()-3).getY();
//                int type=mBoard.pieceList.get(mBoard.pieceList.size()-3).getType();
//                System.out.println("mBoard.pieceList.size()-1="+(mBoard.pieceList.size()-1));
                mBoard.pieceList.remove(mBoard.pieceList.size()-1);
//                System.out.println("mBoard.pieceList.size()-1="+(mBoard.pieceList.size()-1));
                nowBoardArray[y1][x1]=0;

//                System.out.println(mBoard.pieceList.get(mBoard.pieceList.size()-1).getType()+"这是哈哈哈哈哈哈哈");


//                    nowBoardArray[x1][y1]=null;
//                mBoard.pieceList.get(mBoard.pieceList.size()-1).setX(x);
//                mBoard.pieceList.get(mBoard.pieceList.size()-1).setX(y);
//                nowBoardArray[x3][y3]=type;
                if(mBoard.pieceList.size()>=0){

                    mBoard.repaint();
                }
                if(piecetype==1){
                    piecetype=2;
                }else if(piecetype==2){
                    piecetype=1;
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


    //处理鼠标点击事件的方法,对棋子的落点进行处理
    private void play1(MouseEvent e) {
        if(piecetype!=-1){
            //若游戏开始
            int length=mBoard.getLength();//格子的边长
            //计算出鼠标点击事件相对于棋盘的位置
            double x1=(double)(e.getX()-mBoard.margin)/length;
            double y1=(double)(e.getY()-mBoard.margin)/length;
            int x2=(int)Math.rint(x1);
            int y2=(int)Math.rint(y1);
            //给棋盘添加棋子并且传入棋子的相对于棋盘的坐标
            /**
             * 落子需要的条件：
             * 1、该位置没有其他棋子
             * 2、不能超过棋盘的边界区域
             */
            System.out.println("1你的棋子坐标："+y2+" "+x2);//要反转一下x和y
            if(x2>=0&&y2>=0&&x2<mBoard.getBoard_size()&&y2<mBoard.getBoard_size()&&nowBoardArray[y2][x2]==0){
                nowBoardArray[y2][x2]=1;//person一直是1，因为调用鼠标点击事件只能是人自己调用,被占据的为1
                System.out.println("[1]="+nowBoardArray[y2][x2]);
                mBoard.addPieces(new chess2.chessPiece(x2,y2,1));
                //添加完棋子之后需要将棋盘上二维数组的对应位置占据
                if(!ifWin(y2,x2,1)){
                    // play2(e);
                    piecetype=2;
                }

                else {
                    JOptionPane.showMessageDialog(null,"黑子赢了哦！","tip",JOptionPane.PLAIN_MESSAGE);
                    end();
                }

            }

        }
    }

    private void play2(MouseEvent e) {
        if(piecetype!=-1){
            //若游戏开始
            int length=mBoard.getLength();//格子的边长
            //计算出鼠标点击事件相对于棋盘的位置
            double x1=(double)(e.getX()-mBoard.margin)/length;
            double y1=(double)(e.getY()-mBoard.margin)/length;
            int x2=(int)Math.rint(x1);
            int y2=(int)Math.rint(y1);
            //给棋盘添加棋子并且传入棋子的相对于棋盘的坐标
            /**
             * 落子需要的条件：
             * 1、该位置没有其他棋子
             * 2、不能超过棋盘的边界区域
             */
            System.out.println("2你的棋子坐标："+y2+" "+x2);//要反转一下x和y
            if(x2>=0&&y2>=0&&x2<mBoard.getBoard_size()&&y2<mBoard.getBoard_size()&&nowBoardArray[y2][x2]==0){
                nowBoardArray[y2][x2]=2;//person一直是1，因为调用鼠标点击事件只能是人自己调用,被占据的为1
                System.out.println("[1]="+nowBoardArray[y2][x2]);
                mBoard.addPieces(new chessPiece(x2,y2,2));
                //添加完棋子之后需要将棋盘上二维数组的对应位置占据
                if(!ifWin(y2,x2,2)){
                   // play1(e);
                    piecetype=1;
                }

                else{
                    JOptionPane.showMessageDialog(null,"白子赢了哦！","tip",JOptionPane.PLAIN_MESSAGE);
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

    //结束算法
    public void end(){
        for(int []x:nowBoardArray){
            Arrays.fill(x,0);
        }
        //person=1;
        mBoard.clear();
        type=-1;
        begin_game.setEnabled(true);
        piecetype=1;
    }

    public static void main(String[] args) {
        new mainGamePP();
    }
}
