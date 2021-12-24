package world;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class World implements Serializable {
    private Tile[][] tiles;
    private int width;
    private int height;
    private Map<Integer, Creature> players;
    private List<Bomb> bombs;
    private Factory factory;
    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.players = new HashMap<>();
        this.factory = new Factory(this);
        this.bombs = new ArrayList<>();
    }
    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }
    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
    public Tile[][] getTiles(){
        return tiles;
    }
    public Tile tileType(int x, int y){
        return this.tiles[x][y];
    }

    public void addPlayer(int id){
        //Creature player = new Creature(id,id,this);
        Creature player = factory.createCreature(id);
        this.players.put(id,player);
    }
    public boolean addBomb(int id){
        Creature player = this.players.get(id);
        Bomb bomb = this.bomb(player.x(),player.y());
        if(bomb == null) {
            if (player.getBombNum() > 0) {
                factory.newBomb(player.x(), player.y(), player);
                player.modifyBombNum(-1);
                return true;
            }
        }
        return false;

    }
    public void addBombAtLocation(Bomb bomb,int x,int y){
        bomb.setX(x);
        bomb.setY(y);
        this.bombs.add(bomb);
        Thread t = new Thread(bomb);
        t.start();
    }
    public void removePlayer(int id){
        this.players.remove(id);
    }
    public Creature findPlayer(int id){
        return this.getPlayers().get(id);
    }
    public Map<Integer, Creature> getPlayers(){
        return this.players;
    }

    public void updatePlayerLocation(int id, int e) {
        switch (e) {
            case KeyEvent.VK_LEFT:
                this.getPlayers().get(id).moveBy(-1, 0, Dir.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                this.getPlayers().get(id).moveBy(1, 0, Dir.RIGHT);
                break;
            case KeyEvent.VK_UP:
                this.getPlayers().get(id).moveBy(0, -1, Dir.UP);
                break;
            case KeyEvent.VK_DOWN:
                this.getPlayers().get(id).moveBy(0, 1, Dir.DOWN);
                break;
        }
    }
    public void bombExplode(int x,int y){
        tiles[x][y] = Tile.ExplosionTrace;
    }

    public synchronized Creature creature(int x, int y) {
        for(Map.Entry<Integer, Creature> player: players.entrySet()) {
            if (player.getValue().x() == x && player.getValue().y() == y) {
                return player.getValue();
            }
        }
        return null;
    }
    public synchronized Bomb bomb(int x,int y) throws NullPointerException{
        System.out.println("炸弹数量:" + bombs.size());
        try {
            for (Bomb bomb : this.bombs) {
                //if(bomb == null){
                //    return null;
                //}
                if (bomb.x() == x && bomb.y() == y) {
                    return bomb;
                }
            }
        }catch(NullPointerException e){

        }
        return null;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public void setFloor(int x,int y){
        tiles[x][y] = Tile.FLOOR;
    }
    public void remove(Bomb bomb){
        tiles[bomb.x()][bomb.y()] = Tile.FLOOR;
        this.bombs.remove(bomb);
    }
    public World update(){
        //
        //if(this.getPlayers().size() == 1){
        //
        //}
        return this;
    }
    /*
    public List<Integer> losePlayers(){
        List<Integer> losers = new ArrayList<>();
        for(Map.Entry<Integer, Creature> player: players.entrySet()) {
            if (player.getValue().getHp() <= 0) {

            }
        }
    }

     */

}
