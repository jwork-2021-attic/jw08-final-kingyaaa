package world;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Bomb implements Runnable, Serializable {
    private int x;
    private int y;
    private int radius;
    private Creature host;
    private long startTime;
    private volatile boolean explode;
    private World world;
    private List<Point> explosionTrace;
    private volatile boolean Explode;//volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作
    public Bomb(int x,int y,int radius,Creature host,World world){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.host = host;
        this.startTime = System.currentTimeMillis();
        this.explode = false;
        this.world = world;
        this.explosionTrace = new ArrayList<>();
    }
    @Override
    public void run(){
        try{
            while(this.isExplode() == false && System.currentTimeMillis() - this.startTime < 2000){

            }
            setExplode(true);
            world.remove(this);
            setTrace();
            explode();
            host.modifyBombNum(1);
            Thread.sleep(50);
            recoverTrace();
            System.out.println("Bomb done!");


        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int y() {
        return y;
    }

    public int x() {
        return x;
    }

    public boolean isExplode() {
        return explode;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }

    public World getWorld() {
        return world;
    }
    public void recoverTrace(){
        for(Point p:this.explosionTrace){
            this.getWorld().setFloor(p.x, p.y);
        }
    }

    private List<Point> setTrace(){
        int x = this.x();
        int y = this.y();
        this.explosionTrace.add(new Point(x,y));
        x = this.x() - 1;
        y = this.y();
        while(this.x() - x <= radius){
            if(this.getWorld().tileType(x,y) == Tile.WALL){
                break;
            }
            this.explosionTrace.add(new Point(x,y));
            x--;
        }
        x = this.x() + 1;
        y = this.y();
        while(x - this.x() <= radius){
            if(this.getWorld().tileType(x, y) == Tile.WALL){
                break;
            }
            this.explosionTrace.add(new Point(x,y));
            x++;
        }
        x = this.x();
        y = this.y() - 1;
        while(this.y() - y <= radius){
            if(this.getWorld().tileType(x, y) == Tile.WALL){
                break;
            }
            this.explosionTrace.add(new Point(x,y));
            y--;
        }
        x = this.x();
        y = this.y() + 1;
        while(y - this.y() <= radius){
            if(this.getWorld().tileType(x, y) == Tile.WALL){
                break;
            }
            this.explosionTrace.add(new Point(x,y));
            y++;
        }
        return this.explosionTrace;
    }
    public void explode(){
        for(Point p:this.explosionTrace){
            //System.out.println(p.x + " " +  p.y);
            this.getWorld().bombExplode(p.x, p.y);
            Creature otherCreature = this.getWorld().creature(p.x,p.y);
            Bomb bomb = this.getWorld().bomb(p.x,p.y);
            if(otherCreature != null){
                System.out.println("Bomb explode to " + otherCreature);
                //暂时未实现攻击玩家
                otherCreature.getAttack(-1);
            }
            if(bomb != null && (bomb.x() != this.x() || bomb.y() != this.y()) && bomb.isExplode() == false){
                System.out.println("检测到有炸弹");
                detonateBomb(p.x, p.y);
            }
        }
    }
    public void detonateBomb(int x,int y){
        List<Bomb> bombs = this.getWorld().getBombs();
        for(Bomb bomb :bombs){
            if(bomb.x() == x && bomb.y() == y){
                System.out.println("引爆炸弹,是 " + bomb.hashCode());
                bomb.setExplode(true);
            }
        }
    }

    public int getRadius() {
        return radius;
    }
}
