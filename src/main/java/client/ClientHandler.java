package client;

import screen.GameFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import response.*;
import world.*;
import request.LoginRequest;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class ClientHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //直接将msg强转为ResponseEntity
        try {

            //System.out.println("收到Msg");
            if(msg instanceof LoginResponse){
                if(GameFrame.INSTANCE.myPlayerId() == 0) {
                    LoginResponse loginResp = (LoginResponse)msg;
                    System.out.println("收到我的id");
                    System.out.println(loginResp.getId());
                    if(loginResp.getId() == 0){
                        GameFrame.INSTANCE.loginException();
                    }
                    else
                        GameFrame.INSTANCE.startGame(loginResp.getId());
                }
            }
            else if(msg instanceof StartGameResponse){
                if(GameFrame.INSTANCE.myPlayerId() != 0){
                    GameFrame.INSTANCE.setGameState(1);
                    //开始播放音乐
                    try {
                        //if(PlayScreen.INSTANCE.isGameState() == 1) {
                        GameFrame.clip.start();
                        GameFrame.clip.loop(100);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            else if(msg instanceof ResourcesResponse){
                //if 游戏正在进行中 则更新地图 否则不处理
                //System.out.println("收到地图更新消息");
                if(GameFrame.INSTANCE.isGameState() == 1) {
                    ResourcesResponse resourcesResp = (ResourcesResponse) msg;
                    //加载地图到客户端
                    World world = ((ResourcesResponse) msg).getWorld();
                    //游戏进行中
                    //if(world.getPlayers().size() > 1) {
                    GameFrame.INSTANCE.setGameState(1);
                    GameFrame.INSTANCE.UpdateWorld(world);
                }
                //}
                //else{
                //    GameFrame.INSTANCE.setGameState(false);
                //}
            }
            else if(msg instanceof WinGameResponse){
                WinGameResponse winGameResponse = (WinGameResponse) msg;
                if(winGameResponse.getId() == GameFrame.INSTANCE.myPlayerId()){
                    //切换到GameFrame的WinScreen
                    GameFrame.INSTANCE.setGameState(2);
                    GameFrame.INSTANCE.winScreen();
                    GameFrame.clip.close();
                }
            }
            else if(msg instanceof LoseGameResponse){
                LoseGameResponse loseGameResponse = (LoseGameResponse) msg;
                if(loseGameResponse.getId() == GameFrame.INSTANCE.myPlayerId()){
                    //切换到GameFrame的LoseScreen
                    GameFrame.INSTANCE.setGameState(2);
                    GameFrame.INSTANCE.loseScreen();
                    GameFrame.clip.close();
                }
            }
            else{

            }
        }finally {//最后如果没有回写记得释放
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }
    @Override
    public void channelActive (ChannelHandlerContext ctx)throws Exception {
        ctx.writeAndFlush(new LoginRequest(0));
        System.out.println("请求Id");
    }
}