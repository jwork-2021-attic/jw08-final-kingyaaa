package request;

import java.io.Serializable;

public class BombRequst implements Serializable {
    private int id;
    private int keyCode;
    public BombRequst(int id,int keyCode){
        this.id = id;
        this.keyCode = keyCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
