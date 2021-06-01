package chess2;

public class chessPiece {
    private int x;//分别是x,y坐标，且坐标是棋盘上的以有边距的那个点开始
    private int y;
    private int type;//1表示黑子，2表示白子

    public chessPiece(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
