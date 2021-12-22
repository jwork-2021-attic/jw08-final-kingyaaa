public class ServerMain {
    public static final ServerMain INSTANCE = new ServerMain();
    private Server server;
    private World world;
    public ServerMain(){
        server = new Server();
        //初始化地图
        createWorld();
    }
    public static void main(String[] args) throws Exception{
        ServerMain serverMain = ServerMain.INSTANCE;
        serverMain.server.serverStart();
    }
    public void createWorld(){
        world = new WorldBuilder(20,20).makeMap().build();
    }
    public World getWorld(){
        return this.world;
    }
}
