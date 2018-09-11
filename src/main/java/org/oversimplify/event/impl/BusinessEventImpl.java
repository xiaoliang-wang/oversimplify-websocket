package org.oversimplify.event.impl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.oversimplify.enumeration.SendModeEnum;
import org.oversimplify.event.BusinessEvent;
import org.oversimplify.event.SocketEvent;
import org.oversimplify.model.ChannelInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 王晓亮
 * @date 2018/9/3 18:23
 */
public class BusinessEventImpl implements BusinessEvent {


    private final ChannelGroup channelGroup;
    private final ConcurrentMap<String, List<ChannelInfo>> userChannelMap;
    private final SocketEvent socketEvent;

    public BusinessEventImpl(ChannelGroup channelGroup, ConcurrentMap<String, List<ChannelInfo>> userChannelMap, SocketEvent socketEvent){

        this.channelGroup = channelGroup;
        this.userChannelMap = userChannelMap;
        this.socketEvent = socketEvent;

    }


    public void sendMsg(String userId, String msg) {
        sendMsg(userId, null, msg);
    }

    public void sendMsg(String userId, String clientType, String msg) {
        List<ChannelInfo> channelInfoList = userChannelMap.get(userId);
        if(null == channelInfoList || channelInfoList.isEmpty()){
            return;
        }
        for (ChannelInfo channelInfo: channelInfoList) {
            if(null == clientType || clientType.equals(channelInfo.getClientType())){
                final Channel channel = channelGroup.find(channelInfo.getChannelId());
                if(null != channel && channel.isOpen()){
//                    new TextWebSocketFrame(msg)不能在循环外实例化，否则出问题
                    ChannelFuture channelFuture = channel.writeAndFlush(new TextWebSocketFrame(msg));

                    if(channelFuture.isSuccess()){
                        socketEvent.sendMessageFuture(null == clientType?SendModeEnum.SEND_BY_USER:SendModeEnum.SEND_BY_USER_AND_CLIENTTYPE,userId ,clientType ,msg ,true );
                    }else {
                        socketEvent.sendMessageFuture(null == clientType?SendModeEnum.SEND_BY_USER:SendModeEnum.SEND_BY_USER_AND_CLIENTTYPE,userId ,clientType ,msg ,false );
                    }
                }

            }
        }
    }

    public void broadcast(String msg) {
        ChannelGroupFuture channelGroupFuture = channelGroup.writeAndFlush(new TextWebSocketFrame(msg));
//        结果弄反了，不知道怎么回事。求证中
        if(channelGroupFuture.isSuccess()){
            socketEvent.sendMessageFuture(SendModeEnum.BROADCAST,null ,null ,msg ,false );
        }else {
            socketEvent.sendMessageFuture(SendModeEnum.BROADCAST,null ,null ,msg ,true );
        }
    }

    public void broadcast(String clientType, String msg) {

//        TODO 需要优化，否则在并发量和连接数大了以后可能会出问题。优化方案：每种客户端的Channel放到一个channelGroup中

        for(Map.Entry<String,List<ChannelInfo>> channelInfoList : userChannelMap.entrySet()){

            if(null == channelInfoList.getValue() || channelInfoList.getValue().isEmpty()){
                continue;
            }
            for (ChannelInfo channelInfo: channelInfoList.getValue()) {
                if(clientType.equals(channelInfo.getClientType())){
//                    new TextWebSocketFrame(msg)不能在循环外实例化，否则出问题
                    ChannelFuture channelFuture = channelGroup.find(channelInfo.getChannelId()).writeAndFlush(new TextWebSocketFrame(msg));
                    if(channelFuture.isSuccess()){
                        socketEvent.sendMessageFuture(SendModeEnum.BROADCAST_CLIENTTYPE,channelInfoList.getKey() ,clientType ,msg ,true );
                    }else {
                        socketEvent.sendMessageFuture(SendModeEnum.BROADCAST_CLIENTTYPE,channelInfoList.getKey() ,clientType ,msg ,false );
                    }
                }
            }

        }
    }

    public void close(String userId) {
        close(userId,null );
    }

    public void close(String userId, String clientType) {
        List<ChannelInfo> channelInfoList = userChannelMap.get(userId);
        if(null == channelInfoList || channelInfoList.isEmpty()){
            return;
        }
        for (ChannelInfo channelInfo: channelInfoList) {
            if(null == clientType || clientType.equals(channelInfo.getClientType())){

                close(channelInfo.getChannelId());
            }
        }
    }

    public void close(ChannelId channelId) {
        if(null == channelId){
            return;
        }
        Channel channel = channelGroup.find(channelId);
        if(null != channel){
            channel.close();
        }
    }
}
