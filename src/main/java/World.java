import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class World implements Serializable {
    private Tile[][] tiles;
    private int width;
    private int height;
    private Map<Integer,Creature> creatures;
    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new HashMap<>();
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
    public Tile tileType(int x,int y){
        return this.tiles[x][y];
    }

    public void addPlayers(int id){
        Creature player = new Creature(id,id,this);
        this.creatures.put(id,player);
    }
    public Creature findPlayer(int id){
        return this.getPlayers().get(id);
    }
    public Map<Integer,Creature> getPlayers(){
        return this.creatures;
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
}
