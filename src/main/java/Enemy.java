public class Enemy {
    private int x;
    private int y;
    private Dir dir;
    public Enemy(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = Dir.DOWN;
    }

    public Dir getDir() {
        return dir;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
