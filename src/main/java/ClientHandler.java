import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //直接将msg强转为ResponseEntity
        try {
            //ResponseEntity entity = (ResponseEntity) msg;
            //System.out.println(entity.getId() + " " + entity.getName());
            System.out.println("收到msg");

            if(msg instanceof LoginResponse){
                if(PlayScreen.INSTANCE.myPlayerId() == 0) {
                    LoginResponse loginResp = (LoginResponse)msg;
                    System.out.println("收到我的id");
                    System.out.println(loginResp.getId());
                    PlayScreen.INSTANCE.startGame(loginResp.getId());
                }
            }
            else if(msg instanceof ResourcesResponse){
                System.out.println("收到地图更新信息");
                ResourcesResponse resourcesResp = (ResourcesResponse) msg;
                System.out.println(((ResourcesResponse) msg).getWorld());
                //加载地图到客户端
                World world = ((ResourcesResponse) msg).getWorld();
                PlayScreen.INSTANCE.UpdateWorld(world);
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