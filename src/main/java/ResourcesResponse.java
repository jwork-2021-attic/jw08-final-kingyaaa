import java.io.Serializable;

public class ResourcesResponse implements Serializable {
    private int id;
    private World world;
    public ResourcesResponse(int id,World world){
        this.id = id;
        this.world = world;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
