import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.awt.event.KeyEvent;

public class ServerHandler extends ChannelInboundHandlerAdapter{
    private static int registeredID = 0;
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
               LoginResponse loginResp = new LoginResponse(++registeredID);
               Server.clients.writeAndFlush(loginResp);
               ServerMain.INSTANCE.getWorld().addPlayers(registeredID);
            }
            else if(msg instanceof ResourcesRequest){
                int id = ((ResourcesRequest) msg).getId();
                System.out.println("玩家 " + id + "请求地图资源");
                ResourcesResponse resourcesResp = new ResourcesResponse(id,ServerMain.INSTANCE.getWorld());
                Server.clients.writeAndFlush(resourcesResp);
                System.out.println("资源传回");
            }
            else if(msg instanceof PlayerMoveRequest){
                System.out.println("有按键信息传入");
                int id = ((PlayerMoveRequest)msg).getId();
                int e = ((PlayerMoveRequest)msg).getKeyCode();
                ServerMain.INSTANCE.getWorld().updatePlayerLocation(id,e);
                //更新地图信息
                ResourcesResponse resourcesResp = new ResourcesResponse(id,ServerMain.INSTANCE.getWorld());
                Server.clients.writeAndFlush(resourcesResp);
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