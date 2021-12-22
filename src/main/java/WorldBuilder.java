
public class WorldBuilder {
    private int width;
    private int height;
    private Tile[][] tiles;
    public WorldBuilder(int width, int height){
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }
    public World build(){
        return new World(tiles);
    }
    private WorldBuilder drawTiles(){
        //System.out.println(this.width);
        //System.out.println(this.height);
        for(int width = 0;width < this.width;width++){
            for(int height = 0;height < this.height;height++){
                if(width == 0 || width == this.width - 1){
                    tiles[width][height] = Tile.WALL;
                }
                else if(height == 0 || height == this.height - 1){
                    tiles[width][height] = Tile.WALL;
                }
                //else if(width % 2 == 0 && height % 2 == 0){
                //    tiles[width][height] = Tile.WALL;
                //}
                else 
                    tiles[width][height] = Tile.FLOOR;
            }
        }
        /*
        for(int width = 0;width < this.width;width++){
            for(int height = 0;height < this.height;height++){
                if(tiles[width][height] == Tile.WALL){
                    System.out.print(1);
                }
                if(tiles[width][height] == Tile.FLOOR){
                    System.out.print(0);
                }
            }
            System.out.print("\n");
        }
        */
        return this;
    }
    public WorldBuilder makeMap(){
        return drawTiles();
    }
}
