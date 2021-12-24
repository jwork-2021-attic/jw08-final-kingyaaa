package response;

import java.io.Serializable;

public class LoseGameResponse implements Serializable {
    private int id;
    public LoseGameResponse(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
