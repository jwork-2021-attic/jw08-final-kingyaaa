import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Map;

public class MyPanel extends JPanel {
    private PlayScreen screen;
    private Image image;
    public MyPanel(PlayScreen screen){
        this.screen = screen;
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(16);
                        repaint();
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */
    }

    @Override 
    public void paint(Graphics g){
        super.paint(g);
        if(PlayScreen.INSTANCE.myPlayerId() == 0){
            g.drawString("等待连接中...",10,10);
            return;
        }
        displayTiles(this.screen.getWorld(),g);
        displayPlayers(this.screen.getWorld().getPlayers(),g);
        //displayEnemy(this.screen.getEnemyList(),g);
    }

    public void displayTiles(World world,Graphics g){
        String path;
        int unit = screen.getUnit();
        //System.out.print(world.width());
        //System.out.print(world.height());
        //System.out.print("\n");
        for(int x = 0;x < world.width();x++){
            for(int y = 0;y < world.height();y++){
                if(world.tileType(x,y) == Tile.FLOOR){
                    path = "grass1.png";
                }
                else if(world.tileType(x,y) == Tile.WALL){
                    //System.out.println(x + " " + y);
                    path = "wall.png";
                } 
                else{
                    path = null;
                }
                image = getImage(path).getImage();
                //System.out.print("(" + unit * x + " " + unit * y + ") ");
                g.drawImage(image, unit * x, unit * y,this);
            }
            //System.out.print("\n");
        }
    }
    public ImageIcon getImage(String path){
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        return icon;
    }

    public void displayPlayers(Map<Integer,Creature> players, Graphics g){
        for(Map.Entry<Integer,Creature> player: players.entrySet()) {
            if (player.getValue().getX() == 0 && player.getValue().getY() == 0) {
                return;
            }
            String path;
            int unit = screen.getUnit();
            if (player.getValue().getDir() == Dir.UP) {
                path = "Player" + player.getKey() + "-up.png";
            } else if (player.getValue().getDir() == Dir.DOWN) {
                path = "Player" + player.getKey() + "-down.png";
            } else if (player.getValue().getDir() == Dir.LEFT) {
                path = "Player" + player.getKey() + "-left.png";
            } else {
                path = "Player" + player.getKey() + "-right.png";
            }
            image = getImage(path).getImage();
            //System.out.println(player.getX() + " " + player.getY());
            g.drawImage(image, player.getValue().getX() * unit, player.getValue().getY() * unit, 16, 16, this);
        }
    }
    /*
    public void displayEnemy(ArrayList<Enemy> enemyList, Graphics g) {
        for (Enemy enemy : enemyList) {
            if (enemy.getX() == 0 && enemy.getY() == 0) {
                return;
            }
            String path;
            int unit = screen.getUnit();
            if (enemy.getDir() == Dir.UP) {
                path = "Player2-up.png";
            } else if (enemy.getDir() == Dir.DOWN) {
                path = "Player2-down.png";
            } else if (enemy.getDir() == Dir.LEFT) {
                path = "Player2-left.png";
            } else {
                path = "Player2-right.png";
            }
            image = getImage(path).getImage();
            //System.out.println(player.getX() + " " + player.getY());
            g.drawImage(image, enemy.getX() * unit, enemy.getY() * unit, 16, 16, this);
        }
    }
    */

}   
