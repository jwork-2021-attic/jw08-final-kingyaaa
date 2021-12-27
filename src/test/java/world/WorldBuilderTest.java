package world;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorldBuilderTest {

    @Test
    public void buildTest() {
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
        WorldBuilder builder = new WorldBuilder(3,3);
        World world1 = builder.makeMap().build();
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                assertEquals(world.tileType(i,j),world1.tileType(i,j));
            }
        }
    }
}