package world;

import java.io.Serializable;

public class Factory implements Serializable {
    private World world;
    public Factory(World world){
        this.world = world;
    }
    public Creature createCreature(int id){
        if(id == 1) {
            Creature player = new Creature(id, 1, 1, this.world);
            return player;
        }
        if(id == 2){
            Creature player = new Creature(id,8,1,this.world);
            return player;
        }
        if(id == 3){
            Creature player = new Creature(id,8,8,this.world);
            return player;
        }
        if(id == 4){
            Creature player = new Creature(id,1,8,this.world);
            return player;
        }
        return null;

    }
    public Bomb newBomb(int x,int y,Creature host){
        int radius = 1 + host.getScore() / 500;
        Bomb bomb = new Bomb(x,y,radius,host,world);
        world.addBombAtLocation(bomb,x,y);
        return bomb;
    }
}
