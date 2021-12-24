package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import marshalling.MarshallingCodeFactory;
public class Server{
    public volatile static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private Channel channel;
    public void serverStart() throws InterruptedException {

        //1、创建一个线程组来处理服务器接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        //2、创建一个线程组来进行网络通信
        EventLoopGroup workGroup = new NioEventLoopGroup();

        //3、创建辅助工具类Bootstrap，用于服务端通道的配置
        ServerBootstrap sb = new ServerBootstrap();
        sb.group(bossGroup, workGroup)
                .handler(new LoggingHandler()) //设置日志
                .channel(NioServerSocketChannel.class) //指定NIO模式
                .option(ChannelOption.SO_BACKLOG, 1014) //设置tcp缓冲区大小
                //.option(ChannelOption.SO_SNDBUF, 32*1024) //设置发送缓冲区大小
                .option(ChannelOption.SO_RCVBUF,32*1024) //设置接收缓冲区大小
                //.option(ChannelOption.SO_KEEPALIVE, true) //保持连接（长连接）
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //添加mashalling的编解码
                        ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
                        ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
                        ch.pipeline().addLast(new ServerHandler()); //配置具体的数据处理类
                    }

                });

        ChannelFuture f = sb.bind(9876).sync(); //绑定端口，客户端连接需要知道端口
        channel = f.channel();
        //异步等待关闭(记得不是close()方法)
        f.channel().closeFuture().sync();

        //当通道关闭后，将线程组也关闭
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
    public synchronized void send(Object resp){
        clients.writeAndFlush(resp);
    }

}
 