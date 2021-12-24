package request;

import java.io.Serializable;

public class CloseRequest implements Serializable {
    private int id;
    public CloseRequest(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
