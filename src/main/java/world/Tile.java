package world;

public enum Tile {
    FLOOR,//grass
    WALL,
    ExplosionTrace,
    BOUNDS;//bound
    public boolean isGround() {
        return this != Tile.WALL;
    }
}
