package request;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.security.Key;

public class PlayerMoveRequest implements Serializable {
    private int id;
    private int keyCode;
    public PlayerMoveRequest(int id,int keyCode){
        this.id = id;
        this.keyCode = keyCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //public KeyEvent getE() {
    //    return e;
    //}

    //public void setE(KeyEvent e) {
    //    this.e = e;
    //}

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
