import javax.swing.*;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PlayScreen extends JFrame implements KeyListener {
    public static final PlayScreen INSTANCE = new PlayScreen(320,320,16);
    private int screenWidth;
    private int screenHeight;
    private int unit;
    private MyPanel myPanel;
    private World world;
    private int playerId;
    private Client client;

    public PlayScreen(int width,int height,int unit){
        this.client = new Client();

        this.screenWidth = width;
        this.screenHeight = height;
        this.unit = unit;

        this.myPanel = new MyPanel(this);
        this.add(myPanel);

        this.setSize(screenWidth,screenHeight);
        this.setTitle("Bombman");
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        addKeyListener(this);
        //this.myPanel.repaint();

        Dimension screenSizeInfo = Toolkit.getDefaultToolkit().getScreenSize();
        int leftTopX = ((int) (screenSizeInfo.getWidth()) - this.getWidth()) / 2;
        int leftTopY = ((int) screenSizeInfo.getHeight() - this.getHeight()) / 2;
        this.setLocation(leftTopX, leftTopY);

    }
    public void updateWorld(){
        myPanel.repaint();
    }

    public int getUnit(){
        return this.unit;
    }
    public void respondToUserInput(KeyEvent key) {
        PlayerMoveRequest request = new PlayerMoveRequest(myPlayerId(),key.getKeyCode());
        client.send(request);
    }
    public World getWorld(){
        return this.world;
    }

    public void setPlayerId(int id){
        this.playerId = id;
    }
    public int myPlayerId(){
        return this.playerId;
    }

    public void startGame(int id){
        this.playerId = id;
        ResourcesRequest requ = new ResourcesRequest(this.playerId);
        client.send(requ);
        System.out.println("请求资源");
    }
    //更新地图，重画一次
    public void UpdateWorld(World world){
        this.world = world;
        this.myPanel.repaint();
    }
    public void connnectToServer() throws Exception{
        PlayScreen.INSTANCE.client.connect();
    }

    public static void main(String[] args) throws Exception{
        PlayScreen.INSTANCE.connnectToServer();
    }
    /**
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        respondToUserInput(e);
        //repaint();
    }

    /**
     *
     * @param e
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {

    }
}
