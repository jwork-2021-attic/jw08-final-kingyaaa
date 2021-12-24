package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import marshalling.MarshallingCodeFactory;

public class Client {
    private Channel channel;
    public void connect() throws InterruptedException {
        //和服务端不一样，客户端只需要一个线程组来处理网络通信即可
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //创建辅助类，和服务器的不一样，服务端的是ServerBootStrap，而客户端的是BootStrap
        Bootstrap bs = new Bootstrap();
        bs.group(workGroup)
                .channel(NioSocketChannel.class) //设置tcp缓冲区大小
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        //添加mashalling的编解码
                        ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
                        ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });

        //连接服务端
        ChannelFuture cf = bs.connect("127.0.0.1", 9876).sync();
        channel = cf.channel();
        //给服务端写数据
        //for(int i=0;i<10;i++){
        //    RequestEntity entity = new RequestEntity(Integer.toString(i), "Client.Client"+i);
        //    cf.channel().writeAndFlush(entity);
        //}

        //异步监听管道的关闭，如果关闭了就往下执行
        cf.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
    }
    public void send(Object requ){
        channel.writeAndFlush(requ);
    }
}