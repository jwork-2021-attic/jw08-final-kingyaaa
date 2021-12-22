import java.io.Serializable;

public class ResourcesRequest implements Serializable {
    private int id;
    public ResourcesRequest(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
