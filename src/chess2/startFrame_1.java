package chess2;

//import chess2.scoreFrame;

import com.sun.org.apache.xerces.internal.util.URI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class startFrame extends JFrame {

    JButton ppgame;//人人对战
    JButton pcgame;//人机对战
    JButton score;//战绩
    JButton exit;//退出游戏
    JButton sound;//音量键
    JButton instruction;//说明书
    static Music music ;//播放音乐
    static int  musicflag ;//判断是否播放了音乐，已播放为1，没有播放为0
    static int  musicflag1  ;//0需要开音乐，1不需要

    public startFrame(){
        super("五子棋游戏");//标题
        setBounds(400, 40, 890, 750);//宽高位置
        this.setResizable(false);//设置此窗体是否可由用户调整大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
        MyPanel p = new MyPanel();//总面板
        p.setLayout(new BorderLayout());//设置布局
        //设置背景色
        Color background = Color.decode("#F7D99D");


        Font Fonts = new Font("幼圆", Font.BOLD, 16);
        //添加GUI界面的一些相关组件
        JPanel p1 = new JPanel();
        ppgame = new JButton("人人对战");
        pcgame = new JButton("人机对战");
        score = new JButton("战绩");
        exit = new JButton("退出游戏");
        //box的设计
        Box toolbox =Box.createHorizontalBox();
        URL Url1 = startFrame.class.getResource("/chess2/images/yinliang1.png");
        URL Url2 = startFrame.class.getResource("/chess2/images/shuomingshu.png");
        ImageIcon yinliang1 = new ImageIcon(Url1);//音量键图标
        ImageIcon shuomingshu = new ImageIcon(Url2);//说明书
        if(musicflag==0){
            sound = new JButton("音量关");//音量键按钮
        }
       else{
            sound = new JButton("音量开");//音量键按钮
        }
        instruction = new JButton("游戏说明");//说明书按钮
        sound.setContentAreaFilled(false);
        sound.setFocusPainted(false);
        instruction.setContentAreaFilled(false);
        instruction.setFocusPainted(false);
        sound.setIcon(yinliang1);
        instruction.setIcon(shuomingshu);
        toolbox.setBackground(background);
        toolbox.add(sound,BorderLayout.WEST);//音量键按钮位置
        toolbox.add(instruction,BorderLayout.EAST);//说明书按钮位置

        p1.add(toolbox);
        p1.add(ppgame);
        p1.add(pcgame);
        p1.add(score);
        p1.add(exit);

        ppgame.setFont(Fonts);
        pcgame.setFont(Fonts);
        score.setFont(Fonts);
        exit.setFont(Fonts);
        sound.setFont(Fonts);
        instruction.setFont(Fonts);

       ppgame.setBackground(Color.white);
        pcgame.setBackground(Color.white);
        exit.setBackground(Color.white);
        score.setBackground(Color.white);
        sound.setBackground(Color.white);
        instruction.setBackground(Color.white);

       ppgame.setIcon(new ImageIcon("D:\\javadesign3\\workplace_IDEA1\\src\\chess2\\images\\ppgame.png"));
       pcgame.setIcon(new ImageIcon("D:\\javadesign3\\workplace_IDEA1\\src\\chess2\\images\\pcgame.png"));
        exit.setIcon(new ImageIcon("/chess2/images/exit.png"));
       score.setIcon(new ImageIcon("D:\\javadesign3\\workplace_IDEA1\\src\\chess2\\images\\score.png"));



        GridLayout gridlayout=new  GridLayout(5,1,0,80);
        gridlayout.setVgap(70);//设置这个布局的内部元素的垂直间距
        p1.setLayout(gridlayout);
        ppgame.setPreferredSize(new Dimension(110,40));
        p1.setBackground(background);
        p1.setBorder(new EmptyBorder(70,5,70,30));//填充JPanel边距
        p.add(p1,BorderLayout.EAST);//p1添加进总面板中
        this.add(p);
        //音乐播放
        if(musicflag==0&&musicflag1==0){
            try{
                sound.setText("音量开");
                music = new Music();
                music.setAudioClip(Applet.newAudioClip(new File("D:\\javadesign3\\workplace_IDEA1\\src\\chess2\\music.wav").toURL()));
                music.loop();
            }
            catch (MalformedURLException ee){
                ee.printStackTrace();
            }
            musicflag =1;

        }

        //添加监听事件
        MyActionListener ma1 = new MyActionListener();
        ppgame.addActionListener(ma1);
        pcgame.addActionListener(ma1);
        score.addActionListener(ma1);
        exit.addActionListener(ma1);
        sound.addActionListener(ma1);
        instruction.addActionListener(ma1);
        setVisible(true);
    }

    //按钮监听事件类
    class MyActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("人人对战")){
                new mainGamePP();
                setVisible(false);
            }
            else if(e.getActionCommand().equals("人机对战")){
                new mainGame();
                setVisible(false);
            }
            else if(e.getActionCommand().equals("游戏说明")){
                test2();
            }
            else if(e.getActionCommand().equals("战绩")){
               new scoreFrame();
               setVisible(false);
            }
            else if(e.getActionCommand().equals("退出游戏")){
                test1();
            }
            else if(e.getActionCommand().equals("音量开")){
                sound.setText("音量关");

                music.stop();
                musicflag=0;
                musicflag1=1;
                System.out.println("音量关，musicflag="+musicflag);
            }
            else if(e.getActionCommand().equals("音量关")){
                sound.setText("音量开");
                music.loop();
                musicflag=1;
                musicflag1=0;
                System.out.println("音量开，musicflag="+musicflag);
            }
        }
    }
    //音频内部类
    class Music{
        AudioClip clip = null;
        public AudioClip getAudioClip(){
            return this.clip;
        }
        public void setAudioClip(AudioClip clip){
            this.clip = clip;
        }
        public void play(){
            if(getAudioClip()!=null){
                getAudioClip().play();
            }
        }
        public void loop(){
            if(getAudioClip()!=null){
                getAudioClip().loop();
            }
        }
        public void stop(){
            if(getAudioClip()!=null){
                getAudioClip().stop();
            }
        }
    }
    //确认是否关闭该界面的提示框
    private  void test1(){
        int select = JOptionPane.showConfirmDialog(this, "真的要退出游戏吗？", "提示", JOptionPane.YES_NO_OPTION);

        // select 为用户点的第几个按钮
        if(select == 0)
        {
            System.exit(0);
        }
    }

    //游戏说明提示框
    private void test2()
    {
        JOptionPane.showMessageDialog(this, "五子棋是全国智力运动会竞技项目之一，是一种两人对弈的纯策略型棋类游戏。\n" +
                "五子棋有两种玩法。玩法一：双方分别使用黑白两色的棋子，下在棋盘直线与横线的交叉点上，先形成五子连线者获胜。玩法二：自己形成五子连线就替换对方任意一枚棋子。被替换的棋子可以和对方交换棋子。最后以先出完所有棋子的一方为胜。\n" +
                "五子棋的棋具与围棋通用，是一种传统的棋种。\n" +
                "五子棋容易上手，老少皆宜，而且趣味横生，引人入胜：它不仅能增强思维能力，提高智力，而且富含哲理，有助于修身养性。","游戏说明",-1);
    }

    //自定义面板类
    private class MyPanel extends JPanel
    {
        Image image = null ;

        public MyPanel()
        {
            URL imageUrl = startFrame.class.getResource("/chess2/images/background.jpg");//相对路径
            try{
                image = ImageIO.read(imageUrl);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        @Override
        protected void paintComponent(Graphics g)
        {
            int width = this.getWidth();
            int height = this.getHeight();
            g.clearRect(0, 0, width, height);

            // 画背景图
            g.drawImage(image, 0, 0, width, height, null);


        }
    }
    public static void main(String[]args){new startFrame();}
}
