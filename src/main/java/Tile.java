public enum Tile {
    FLOOR,//grass
    WALL,
    BOUNDS;//bound
    public boolean isGround() {
        return this != Tile.WALL;
    }
}
