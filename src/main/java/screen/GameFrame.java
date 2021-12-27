package screen;

import client.Client;
import request.BombRequst;
import request.CloseRequest;
import request.PlayerMoveRequest;
import request.ResourcesRequest;
import world.World;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;


public class GameFrame extends JFrame implements KeyListener {
    public static final GameFrame INSTANCE = new GameFrame(320,320,32);
    public static AudioInputStream audio;//
    public static Clip clip;
    private int screenWidth;
    private int screenHeight;
    private int unit;
    private MyPanel myPanel;
    private World world;
    private int playerId;
    private Client client;
    private volatile int gameState;//0:未开始 1:进行中 2:结束
    private boolean win;
    private boolean loginCondition;

    public static void main(String[] args) throws Exception{
        GameFrame.INSTANCE.connnectToServer();
    }

    public GameFrame(int width, int height, int unit){
        this.client = new Client();

        this.screenWidth = width;
        this.screenHeight = height;
        this.unit = unit;
        this.gameState = 0;
        this.loginCondition = true;
        this.win = false;
        this.myPanel = new MyPanel(this);
        this.add(myPanel);

        this.setSize(screenWidth,screenHeight);
        this.setTitle("Bombman");
        //this.setResizable(false);
        //关闭窗口时需要发送消息给服务器
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.send(new CloseRequest(myPlayerId()));
                System.exit(0);
            }
        });
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
        addKeyListener(this);
        //this.myPanel.repaint();

        Dimension screenSizeInfo = Toolkit.getDefaultToolkit().getScreenSize();
        int leftTopX = ((int) (screenSizeInfo.getWidth()) - this.getWidth()) / 2;
        int leftTopY = ((int) screenSizeInfo.getHeight() - this.getHeight()) / 2;
        this.setLocation(leftTopX, leftTopY);

        try{

            //audio = AudioSystem.getAudioInputStream(new File("bgm.wav").getAbsoluteFile());
            //audio = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResource("bgm.wav")));
            audio = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("bgm.wav")));
            clip = AudioSystem.getClip();
            clip.open(GameFrame.audio);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateWorld(){
        myPanel.repaint();
    }

    public int getUnit(){
        return this.unit;
    }
    public void respondToUserInput(KeyEvent key) {
        switch(key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                PlayerMoveRequest playerMoveRequest = new PlayerMoveRequest(myPlayerId(), key.getKeyCode());
                client.send(playerMoveRequest);
                break;
            case KeyEvent.VK_SPACE:
                BombRequst bombRequest = new BombRequst(myPlayerId(),key.getKeyCode());
                client.send(bombRequest);
                break;
        }
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
    public void winScreen(){
        this.setWin(true);
        this.myPanel.repaint();
    }
    public void loseScreen(){
        this.setWin(false);
        this.myPanel.repaint();
    }
    public void loginException(){
        this.loginCondition = false;
        this.myPanel.repaint();
    }

    public boolean isLoginCondition() {
        return loginCondition;
    }

    public void connnectToServer() throws Exception{
        GameFrame.INSTANCE.client.connect();
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

    public int isGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public boolean isWin() {
        return win;
    }
    public void setWin(boolean win){
        this.win = win;
    }

}
