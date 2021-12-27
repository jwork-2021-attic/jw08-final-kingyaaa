package world;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WorldTest {

    @Test
    public void addPlayerTest() {
        Factory factory = new Factory(null);
        int id = 2;
        Creature player = factory.createCreature(id);
        Creature player2 = new Creature(2,8,1,null);
        assertEquals(player.getId(),player2.getId());
        assertEquals(player.x(),player2.x());
        assertEquals(player.y(),player2.y());
    }

    @Test
    public void removePlayerTest(){
        Tile[][] tiles = new Tile[1][1];
        tiles[0][0] = Tile.WALL;
        World world = new World(tiles);
        Creature creature = new Creature(1,1,1,world);
        world.getPlayers().put(1,creature);
        world.removePlayer(1);
        Creature c2 = world.getPlayers().get(1);
        assertEquals(null,c2);
    }

    @Test
    public void addBombTest() {
        Tile[][] tiles = new Tile[1][1];
        tiles[0][0] = Tile.FLOOR;
        World world = new World(tiles);
        Creature creature = new Creature(1,1,1,world);
        Factory factory = new Factory(world);
        Bomb bomb = factory.newBomb(0,0,creature);
        Bomb bomb2 = new Bomb(0,0,1,creature,world);
        assertEquals(bomb.x(),bomb2.x());
        assertEquals(bomb.y(),bomb2.y());
        assertEquals(bomb.getRadius(),bomb2.getRadius());
    }

    @Test
    public void creatureTest() {
        Tile[][] tiles = new Tile[3][3];
        tiles[0][0] = Tile.WALL;
        tiles[0][1] = Tile.WALL;
        tiles[0][2] = Tile.WALL;
        tiles[1][0] = Tile.WALL;
        tiles[1][2] = Tile.WALL;
        tiles[2][0] = Tile.WALL;
        tiles[2][1] = Tile.WALL;
        tiles[2][2] = Tile.WALL;
        tiles[1][1] = Tile.FLOOR;
        World world = new World(tiles);
        Creature creature = new Creature(1,1,1,world);
        world.getPlayers().put(1,creature);
        Creature c2 = world.creature(1,1);
        assertEquals(c2.getId(),creature.getId());
        assertEquals(c2.x(),creature.x());
        assertEquals(c2.y(),creature.y());
    }

    @Test
    public void tileTest(){
        Tile[][] tiles = new Tile[3][3];
        /*
        0 0 0
        0 1 0
        0 0 0
         */
        tiles[0][0] = Tile.WALL;
        tiles[0][1] = Tile.WALL;
        tiles[0][2] = Tile.WALL;
        tiles[1][0] = Tile.WALL;
        tiles[1][2] = Tile.WALL;
        tiles[2][0] = Tile.WALL;
        tiles[2][1] = Tile.WALL;
        tiles[2][2] = Tile.WALL;
        tiles[1][1] = Tile.FLOOR;
        World world = new World(tiles);
        assertEquals(Tile.BOUNDS,world.tile(-1,-1));
        assertEquals(Tile.BOUNDS,world.tile(3,4));
        assertEquals(Tile.FLOOR,world.tileType(1,1));
    }

    @Test
    public void setFloorTest(){
        Tile[][] tiles = new Tile[1][1];
        tiles[0][0] = Tile.WALL;
        World world = new World(tiles);
        world.setFloor(0,0);
        assertEquals(Tile.FLOOR,world.tileType(0,0));
    }
    @Test
    public void bombExplodeTest(){
        Tile[][] tiles = new Tile[1][1];
        tiles[0][0] = Tile.WALL;
        World world = new World(tiles);
        world.bombExplode(0,0);
        assertEquals(Tile.ExplosionTrace,world.tileType(0,0));
    }


}