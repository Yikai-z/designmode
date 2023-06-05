package org.example.design.mode.nettyexample;

import edu.princeton.cs.algs4.In;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyBasicServerExample {


    public static void main(String[] args) {
        // 主线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 工作线层组
        EventLoopGroup workGroup = new NioEventLoopGroup(4);
        // 构建Netty Server的api
        ServerBootstrap bootstrap = new ServerBootstrap();
        //Bootstrap
        bootstrap.group(bossGroup,workGroup)
            // 指定epoll模型
            .channel(NioServerSocketChannel.class)
            // 具体的工作处理类，负责处理相关的socketChannel的IO就绪事件
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //心跳的hander
                    //编解码
                    //协议处理
                    //消息处理
                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf in=(ByteBuf) msg;
                            byte[] req=new byte[in.readableBytes()];
                            in.readBytes(req);
                            System.out.println("服务端收到的数据："+new String(req,"utf-8"));
                            super.channelRead(ctx, msg);
                        }
                    }).addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.out.println("收到消息-----第二处理器");
                        }
                    });
                }
            });

        try {
            // 同步阻塞等到客户端连接
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            System.out.println("Netty Server Started Success:listener port:8080");
            // 同步等到服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }


}
