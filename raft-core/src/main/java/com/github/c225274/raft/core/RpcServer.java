package com.github.c225274.raft.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollMode;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.apache.log4j.Logger;

public class RpcServer {
    
    private static final Logger LOG = Logger.getLogger(RpcServer.class);
    
    private int port;    

    private EventLoopGroup bossGroup;
    
    private EventLoopGroup workerGroup;
    
    private ServerBootstrap bootstrap;
    
    public RpcServer(int port){
        this.port = port;
        bootstrap=new ServerBootstrap();
        if (Epoll.isAvailable()) {
            bossGroup=new EpollEventLoopGroup();
            workerGroup=new EpollEventLoopGroup();           
            bootstrap.channel(EpollServerSocketChannel.class);
            bootstrap.option(EpollChannelOption.EPOLL_MODE, EpollMode.EDGE_TRIGGERED);
            bootstrap.childOption(EpollChannelOption.EPOLL_MODE, EpollMode.EDGE_TRIGGERED);
            LOG.info("use epoll edge trigger mode");
        }else{
            bossGroup=new NioEventLoopGroup();
            workerGroup=new NioEventLoopGroup();
            bootstrap.channel(NioServerSocketChannel.class);
            LOG.info("use nio mode");
        }
        bootstrap.group(bossGroup, workerGroup).childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                // TODO Auto-generated method stub
                
            }
            
        });
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public void setBossGroup(EventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
    }

    public void setWorkerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
    }
    
}
