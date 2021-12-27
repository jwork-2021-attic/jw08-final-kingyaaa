package world;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreatureTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getBombNumTest(){
        Creature creature = new Creature(0,0,0,null);
        int num = creature.getBombNum();
        assertEquals(num,2);
    }

    @Test
    public void modifyBombNumTest() {
        Creature creature = new Creature(0,0,0,null);
        creature.modifyBombNum(-1);
        int num = creature.getBombNum();
        assertEquals(num,1);
    }

    @Test
    public void moveByTest(){
        Tile[][] tiles = new Tile[3][3];
        tiles[0][0] = Tile.FLOOR;
        tiles[0][1] = Tile.FLOOR;
        tiles[0][2] = Tile.FLOOR;
        tiles[1][0] = Tile.FLOOR;
        tiles[1][2] = Tile.FLOOR;
        tiles[2][0] = Tile.FLOOR;
        tiles[2][1] = Tile.FLOOR;
        tiles[2][2] = Tile.FLOOR;
        tiles[1][1] = Tile.FLOOR;
        World world = new World(tiles);
        Creature creature = new Creature(1,1,1,world);
        world.getPlayers().put(1,creature);
        creature.moveBy(-1,0,Dir.LEFT);
        assertEquals(creature.x(),0);
        assertEquals(creature.y(),1);
    }
}