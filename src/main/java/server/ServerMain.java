package server;

import response.LoseGameResponse;
import response.ResourcesResponse;
import response.WinGameResponse;
import world.Creature;
import world.World;
import world.WorldBuilder;

import java.util.Map;

public class ServerMain {
    public static final ServerMain INSTANCE = new ServerMain();
    private static int playerLimit = 2;
    private Server GameServer;
    private World world;
    public ServerMain(){
        GameServer = new Server();
        //初始化地图
        createWorld();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(33);
                        ResourcesResponse resp = new ResourcesResponse(world.update());
                        if(!GameServer.clients.isEmpty() && ServerHandler.gameState == true) {
                            GameServer.send(resp);
                            if(world.getPlayers().size() == 1){
                                gameOver();
                                ServerHandler.gameState = false;
                            }
                            //if(world.getPlayers().size() == 0){
                            //游戏未完全结束
                            //}
                            //System.out.println("实时重绘消息发送");
                        }
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public static void main(String[] args) throws Exception{
        ServerMain serverMain = ServerMain.INSTANCE;
        serverMain.GameServer.serverStart();
    }
    public void createWorld(){
        world = new WorldBuilder(10,10).makeMap().build();

    }
    public World getWorld(){
        return this.world;
    }

    public void gameOver(){
        //只有一个Player
        long current = System.currentTimeMillis();
        //隔半秒再发
        while(System.currentTimeMillis() - current < 200){

        }
        for(Map.Entry<Integer, Creature> player: world.getPlayers().entrySet()) {
            int id = player.getKey();
            if(id == 1 || id == 3){
                GameServer.send(new WinGameResponse(1));
                //GameServer.send(new WinGameResponse(3));
                GameServer.send(new LoseGameResponse(2));
                //GameServer.send(new LoseGameResponse(4));
            }
            else if(id == 2 || id == 4){
                GameServer.send(new WinGameResponse(2));
                //GameServer.send(new WinGameResponse(4));
                GameServer.send(new LoseGameResponse(1));
                //GameServer.send(new LoseGameResponse(3));
            }
        }
    }
    public int getPlayerLimit(){
        return playerLimit;
    }

}
