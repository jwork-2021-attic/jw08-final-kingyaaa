package response;

import java.io.Serializable;
import world.World;
public class ResourcesResponse implements Serializable {
    //private int id;
    private World world;
    public ResourcesResponse(World world){
        //this.id = id;
        this.world = world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
