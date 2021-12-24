package server;

import request.*;
import response.*;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter{
    private static int registeredID = 0;
    private static int limit = ServerMain.INSTANCE.getPlayerLimit();
    private static boolean[] loginedID = new boolean[limit];
    public static boolean gameState;
    public ServerHandler(){
        //this.limit = ServerMain.INSTANCE.getPlayerLimit();
        //this.loginedID = new boolean[limit];
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        Server.clients.add(ctx.channel());
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            System.out.println("有消息传入");
            //因为有marshalling编解码器，所以可以直接将msg强转
            //RequestEntity entity = (RequestEntity) msg;
            //System.out.println(entity.getId() + " " + entity.getName());
            //ResponseEntity response = new ResponseEntity(entity.getId(), "server" + entity.getId());
            if(msg instanceof LoginRequest){
                System.out.println("收到id请求");
                registeredID = 0;
                for(int i = 0;i < limit;i++){
                   if(loginedID[i] == false){
                       registeredID = i + 1;
                       loginedID[i] = true;
                       break;
                   }
                }
                if(registeredID != 0) {
                   LoginResponse loginResp = new LoginResponse(registeredID);
                   Server.clients.writeAndFlush(loginResp);
                   ServerMain.INSTANCE.getWorld().addPlayer(registeredID);
                   boolean start = true;
                   for(int i = 0;i < limit;i++){
                       if(loginedID[i] == false){
                           start = false;
                       }
                   }
                   if(start == true){
                       //开始游戏
                       gameState = true;
                       StartGameResponse startGameResponse = new StartGameResponse();
                       Server.clients.writeAndFlush(startGameResponse);
                   }
                }
                else{
                    //已满员
                    LoginResponse loginResp = new LoginResponse(0);
                    Server.clients.writeAndFlush(loginResp);

                }
            }
            else if(msg instanceof ResourcesRequest){
                int id = ((ResourcesRequest) msg).getId();

                System.out.println("玩家 " + id + "请求开始游戏");
                if(gameState == true) {
                    //让所有玩家开始游戏
                    ResourcesResponse resourcesResp = new ResourcesResponse(ServerMain.INSTANCE.getWorld());
                    Server.clients.writeAndFlush(resourcesResp);
                    System.out.println("资源传回");
                }
            }
            else if(msg instanceof PlayerMoveRequest){
                //System.out.println("有按键信息传入");
                int id = ((PlayerMoveRequest)msg).getId();
                if(ServerMain.INSTANCE.getWorld().getPlayers().get(id) == null){

                }
                else {
                    int e = ((PlayerMoveRequest) msg).getKeyCode();
                    ServerMain.INSTANCE.getWorld().updatePlayerLocation(id, e);
                }
            }
            else if(msg instanceof BombRequst){
                //玩家id新置一个炸弹
                ServerMain.INSTANCE.getWorld().addBomb(((BombRequst)msg).getId());// == true) {
                    //更新地图信息,炸弹出现
                    //ResourcesResponse resourcesResponse = new ResourcesResponse(ServerMain.INSTANCE.getWorld());
                    //Server.clients.writeAndFlush(resourcesResponse);
                //}
            }
            else if(msg instanceof CloseRequest){
                int id = ((CloseRequest) msg).getId();
                System.out.println("客户端" + ((CloseRequest) msg).getId() + "请求退出");
                Server.clients.remove(ctx.channel());
                ctx.close();
                loginedID[id - 1] = false;
                ServerMain.INSTANCE.getWorld().removePlayer(((CloseRequest) msg).getId());
                //ResourcesResponse resourcesResp = new ResourcesResponse(ServerMain.INSTANCE.getWorld());
                //Server.clients.writeAndFlush(resourcesResp);
            }
            else{
                System.out.println("暂时无法处理此消息");
            }
        }finally{
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws     Exception {
        cause.printStackTrace();
        Server.clients.remove(ctx.channel());
        ctx.close(); //出现异常的话就关闭
    }
}