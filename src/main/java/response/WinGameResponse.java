package response;

import javax.swing.*;
import java.io.Serializable;

public class WinGameResponse implements Serializable {
    private int id;
    public WinGameResponse(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
