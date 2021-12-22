import java.io.Serializable;

public class Creature implements Serializable {
    private int x;
    private int y;
    private Dir dir;
    private World world;
    private int id;
    public Creature(int x, int y, World world){
        this.x = x;
        this.y = y;
        this.dir = Dir.DOWN;
        this.world = world;
        this.id = 0;
    }
    public void moveBy(int mx, int my,Dir dir){
        onEnter(mx,my,dir,world.tile(this.x + mx,this.y + my));
        
    }
    public void onEnter(int mx,int my,Dir dir,Tile tile){
        if(tile.isGround()){
            this.setX(x + mx);
            this.setY(y + my);
            this.setDir(dir);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDir(Dir dir){
        this.dir = dir;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Dir getDir(){
        return this.dir;
    }

}
