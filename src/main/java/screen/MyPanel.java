package screen;
import world.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;
public class MyPanel extends JPanel {
    private GameFrame screen;
    private Image image;
    public MyPanel(GameFrame screen){
        this.screen = screen;
    }

    @Override 
    public void paint(Graphics g){
        super.paint(g);
        if(GameFrame.INSTANCE.myPlayerId() == 0 && GameFrame.INSTANCE.isLoginCondition() == true){
            g.drawString("Waiting connection...",30,30);
            return;
        }
        else if(GameFrame.INSTANCE.isLoginCondition() == false){
            g.drawString("房间已满员",30,30);
            return;
        }
        else if(GameFrame.INSTANCE.isGameState() == 1) {
            displayTiles(this.screen.getWorld(), g);
            displayBombs(this.screen.getWorld().getBombs(), g);
            displayPlayers(this.screen.getWorld().getPlayers(), g);
            return;
        }
        else if(GameFrame.INSTANCE.isGameState() == 2 && GameFrame.INSTANCE.isWin() == true){
            g.drawString("Win Game!",30,30);
            return;
        }
        else if(GameFrame.INSTANCE.isGameState() == 2 && GameFrame.INSTANCE.isWin() == false){
            g.drawString("Lose Game!",30,30);
            return;
        }
    }

    public void displayTiles(World world, Graphics g){
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
                else if(world.tileType(x,y) == Tile.ExplosionTrace){
                    path = "explosionTrace.png";
                }
                else{
                    path = null;
                }
                image = getImage(path).getImage();
                //System.out.print("(" + unit * x + " " + unit * y + ") ");
                g.drawImage(image, unit * x, unit * y,unit,unit,this);
            }
            //System.out.print("\n");
        }
    }
    public ImageIcon getImage(String path){
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        return icon;
    }

    public void displayPlayers(Map<Integer, Creature> players, Graphics g){
        //System.out.println(players.size());
        for(Map.Entry<Integer, Creature> player: players.entrySet()) {
            if (player.getValue().x() == 0 && player.getValue().y() == 0) {
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
            g.drawImage(image, player.getValue().x() * unit, player.getValue().y() * unit, unit, unit, this);
        }
    }

    public void displayBombs(List<Bomb> bombs, Graphics g) {
        if(bombs != null) {
            //System.out.println("现有炸弹数量:" + bombs.size());
            for (Bomb bomb : bombs) {
                if (bomb != null && bomb.isExplode() == false) {
                    String path = "bomb.png";
                    int unit = screen.getUnit();
                    image = getImage(path).getImage();
                    g.drawImage(image, bomb.x() * unit, bomb.y() * unit, unit, unit, this);
                }
            }
        }
    }
}   
