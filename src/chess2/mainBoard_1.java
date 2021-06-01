package chess2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class mainBoard extends JPanel {
    public static final int Board_size=19;
    public int margin=20;//边距
    //采用长度可变的集合来存储棋子,根据其容量来循环画棋子
    public final List<chessPiece> pieceList=new ArrayList<>();
    //还需要创建一个数组用来表示棋盘上被占用的位置
    //成员方法：画棋盘和棋子

    /**
     * 下面一系列都是获取一些必要的元素
     * @return
     */
    //获取一个小格子的边长
    public int getLength(){
        //getHeight直接提供获取整个界面的高度
        return ((getHeight()-2*margin)/(Board_size-1));
    }
    //获取边距
    public int getMargin(){
        return margin;
    }
    //获取棋盘的边长
    public int getBoard_size(){
        return Board_size;
    }
    //棋子边长=格子长度-5
    public int getPieceLength(){
        return getLength()-5;
    }
    /**
     *
     * @param g 画笔工具
     */
    public void paint(Graphics g){
        super.paint(g);
        int length=getLength();//一个格子的边长
        Graphics2D g1=(Graphics2D)g;
        //设置抗锯齿，让画线平滑让棋子更加圆润，不然边缘会有锯齿状
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING ,RenderingHints.VALUE_ANTIALIAS_ON);


        //画棋盘
        for(int i=0;i<Board_size;i++){
            //横线
            g.drawLine(margin,margin+length*i,margin+(Board_size-1)*length,margin+length*i);
            //竖线
            g.drawLine(margin+length*i,margin,margin+length*i,margin+(Board_size-1)*length);
        }

        g1.setColor(new Color(116,88,49));
        //画出五个小棕点
        g1.fillOval(margin+9*getLength()-5, margin+ 9*getLength()-5, 10, 10);
        g1.fillOval(margin+4*getLength()-5, margin+ 4*getLength()-5, 10, 10);
        g1.fillOval(margin+4*getLength()-5, margin+ 15*getLength()-5, 10, 10);
        g1.fillOval(margin+14*getLength()-5, margin+ 4*getLength()-5, 10, 10);
        g1.fillOval(margin+14*getLength()-5, margin+ 15*getLength()-5, 10, 10);

        //调用画棋子的方法，paint是主动被调用，鼠标监听到落子，就调play对落子进行处理，
        // play调用addPiece,让棋子容器加入棋子并调用paint方法
        //paint不断调用drawChessPiece进行每一次鼠标下落棋子的绘制
        drawChessPieces(g1);
    }
    //画棋子
    public void drawChessPieces(Graphics2D g){
        //根据棋子的type属性选择画黑棋子还是白棋子

        //获取fontmetrics对象
        g.setFont(new Font("黑体",Font.PLAIN,18));
        FontMetrics metrics=g.getFontMetrics();
        int ascent = metrics.getAscent();
        int descent = metrics.getDescent();


        for(int i=0;i<pieceList.size();i++){
            chessPiece x=pieceList.get(i);//获取一个棋子对象
            if(x.getType()==1)
                g.setColor(Color.black);//g就是画笔
            else
                g.setColor(Color.white);
            //画棋子，椭圆的最左边那点即在棋盘上的坐标*格子长度+边距-棋子长度的一半
            g.fillOval(x.getX()*getLength()-getPieceLength()/2+margin,x.getY()*getLength()-getPieceLength()/2+margin,getPieceLength(),getPieceLength());

            //画棋子上面的数字
            if(x.getType()==1) g.setColor(Color.white);
            else g.setColor(Color.black);//与棋子颜色相反
            String num = i+1+"";//数组号+1并且转换成字符串

            //数字的坐标和棋子一样=棋子在棋盘上的坐标*格子长度+边距-字符串的一半（以便于居中）
            g.drawString(num,x.getX()*getLength()+margin-metrics.stringWidth(num)/2,x.getY()*getLength()+margin-(ascent+descent)/2+ascent);


        }
    }
    //每次棋子容器中添加棋子
    public void addPieces(chessPiece x){
        pieceList.add(x);
        repaint();
    }


    //清空
    public void clear(){
        pieceList.clear();
        repaint();
    }



    public static void main(String[] args) {
        new mainBoard();
    }

}

