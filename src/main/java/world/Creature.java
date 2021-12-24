package world;

import world.World;

import java.io.Serializable;

public class Creature implements Serializable {
    private int x;
    private int y;
    private Dir dir;
    private World world;
    private int id;
    private int score;
    private int bombNum;
    private int hp;
    public Creature(int id,int x, int y, World world){
        this.x = x;
        this.y = y;
        this.dir = Dir.DOWN;
        this.world = world;
        this.id = id;
        this.score = 0;
        this.bombNum = 2;
        this.hp = 1;
    }
    public void moveBy(int mx, int my, Dir dir){
        Creature otherCreature = world.creature(x + mx, y + my);
        Bomb bomb = world.bomb(x + mx,y + my);
        if(otherCreature == null && bomb == null) {
            onEnter(mx, my, dir, world.tile(this.x + mx, this.y + my));
        }
    }
    public void onEnter(int mx, int my, Dir dir, Tile tile){
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
    public int x(){
        return this.x;
    }
    public int y(){
        return this.y;
    }
    public Dir getDir(){
        return this.dir;
    }

    public int getScore() {
        return score;
    }

    public void modifyScore(int score) {
        this.score += score;
    }

    public int getBombNum() {
        return bombNum;
    }

    public void modifyBombNum(int bombNum) {
        this.bombNum += bombNum;
    }
    public synchronized void getAttack(int gotAttack){
        this.hp += gotAttack;
        if(this.hp <= 0){
            this.world.getPlayers().remove(id);
            System.out.println("Player" + id + "lose");
            System.out.println(this.world.getPlayers().size());
        }
    }

    public int getHp() {
        return hp;
    }
}
