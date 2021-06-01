package chess2;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class scoreFrame extends JFrame {
    JButton Back;//返回主界面
    JMenuBar menuBar;    // 菜单栏
    JMenu menu;       // 菜单
    JMenuItem scoremenu, showmenu; // 菜单项
    JLabel scoreLabel,showLabel;//玩家分数和战绩详情
    MyPanel p;//总面板
    MyPanel p1;//
    JButton back = new JButton("返回主页面");//返回按钮

    public scoreFrame(){
        super("战绩");//标题
        setBounds(400, 40, 890, 750);//宽高位置
        this.setResizable(false);//设置此窗体是否可由用户调整大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
        p = new MyPanel(0);//总面板
        //设置布局

        p.setLayout(new BorderLayout());
        //设置按钮字体大小为加粗，16号字
        Font Fonts = new Font("幼圆", Font.BOLD, 16);
        //设置背景色
        Color background = Color.decode("#F7D99D");
        //初始化菜单内容
        menuBar = new JMenuBar();
        menu = new JMenu("切换按钮");
        scoremenu = new JMenuItem("你的分数");
        showmenu = new JMenuItem("战绩详情");
        menu.add(scoremenu);
        menu.add(showmenu);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        String readscore = "<html><body>"+"您当前在人机对战中获胜的次数为："+readScore()+"<br>"+stringScore(readScore())+"<body></html>";
        scoreLabel = new JLabel(readscore);

        //设置字体
        scoreLabel.setFont(Fonts);
        //面板设置
        p1 = new MyPanel();
        GridLayout gridlayout=new  GridLayout(5,1,0,80);
        gridlayout.setVgap(70);//设置这个布局的内部元素的垂直间距
        p1.setBackground(null);
        p1.setOpaque(false);
        p1.setLayout(gridlayout);
       // back.setPreferredSize(new Dimension(50,20));
        back.setBounds(50,50,10,10);
        p1.add(scoreLabel);
        p1.add(back);
        p1.setBorder(new EmptyBorder(70,5,70,30));//填充JPanel边距
        p.add(p1,BorderLayout.EAST);
        this.add(p);
        //事件监听
        MyActionListener al = new MyActionListener();
        scoremenu.addActionListener(al);
        showmenu.addActionListener(al);
        back.addActionListener(al);
        setVisible(true);
    }

    //创建分数面板
    private MyPanel createScorePanel(){
        String readscore = "<html><body>"+"您当前在人机对战中获胜的次数为："+readScore()+"<br>"+stringScore(readScore())+"<body></html>";
        scoreLabel = new JLabel(readscore);
        return p1;
    }

    //创建战绩详情面板
    private JPanel createShowPanel(){
        JPanel Panel = new JPanel();
        return Panel;
    }
    //设置面板
    private  void setLabel(JPanel panel, JPanel show){
        panel.removeAll();
        panel.add(show,BorderLayout.EAST);
        panel.validate();
        panel.repaint();

    }
    //鼠标点击事件类
    class MyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==scoremenu){
                setLabel(p,createScorePanel());
            }
            if(e.getSource()==showmenu){
                setLabel(p,createShowPanel());
            }
            if(e.getSource()==back){
                new startFrame();
                setVisible(false);
            }
        }
    }
    //读取分数
    private int readScore() {
        File inF = new File("D:\\javadesign3\\workplace_IDEA1\\src\\chess2\\images\\score.txt");
        // File outF = new File("D:\\developer_tools\\IDEA\\workplace_IDEA1\\src\\chess2\\images\\score2.txt");
        char[]ca = new char[(int)inF.length()];//定义存放字符流的数组
        int a = 0;
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
            System.out.println("inF="+(inF.length()));

            for(int i=0;i<1;i++){
                System.out.println(ca[i]);
            }
            String str = new String(ca);
            System.out.println("str="+str);

            a = Integer.parseInt(str);

        }catch(FileNotFoundException e) {
            System.out.println("文件不存在"+e);
        }catch(IOException e) {
            System.out.println("文件读取错误");
        }
        return a;
    }
    //判断战绩
    private String stringScore(int a){
        String s = "";
        if(a>=0&&a<2){
            s=s+"您目前的称号是：蛋！";
        }
        else if(a>=2&&a<5){
            s=s+"您目前的称号是：菜！";
        }
        else if(a>=5&&a<10){
            s=s+"您目前的称号是：小菜鸡！";
        }
        else if(a>=10&&a<20){
            s=s+"您目前的称号是：中等鸡！";
        }
        else if(a>=20&&a<50){
            s=s+"您目前的称号是：高等鸡！";
        }
        else if(a>=50&&a<80){
            s=s+"您目前的称号是：皇鸡！";
        }
        else{
            s=s+"您目前的称号是：传说鸡！";
        }
        return s;
    }
    //自定义面板类
    private class MyPanel extends JPanel
    {
        URL imageUrl;
        Image image = null ;
        int a;
        public MyPanel(){


        }
        public MyPanel(int a)
        {
            this.a=a;
            System.out.println("a="+a);
            if(a==0){
                imageUrl = startFrame.class.getResource("/chess2/images/chicken.jpg");//相对路径
            }
            else{
                //imageUrl = startFrame.class.getResource("/chess2/images/opra.png");//相对路径
            }
            try{
                image = ImageIO.read(imageUrl);
            }catch(Exception e)
            {
                //e.printStackTrace();
                System.out.println("没找到图片"+a);
            }
        }
        @Override
        protected void paintComponent(Graphics g)
        {
            if(a==0){
                int width = this.getWidth();
                int height = this.getHeight();
                g.clearRect(0, 0, width, height);

                // 画背景图
                g.drawImage(image, 0, 0, width, height, null);
            }
            else{
                float f =1;
                Graphics2D graphics2d = (Graphics2D) g.create();
                graphics2d.setComposite(AlphaComposite.SrcOver.derive(f));
                graphics2d.setColor(getBackground());
                graphics2d.fillRect(0, 0, getWidth(), getHeight());
                graphics2d.dispose();

            }


        }
    }
    public  static void main(String[]args){
        new scoreFrame();
    }
}
