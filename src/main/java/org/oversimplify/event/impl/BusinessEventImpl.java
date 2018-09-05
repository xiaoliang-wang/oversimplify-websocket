package org.oversimplify.event.impl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.oversimplify.event.BusinessEvent;
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

    public BusinessEventImpl(ChannelGroup channelGroup, ConcurrentMap<String, List<ChannelInfo>> userChannelMap){

        this.channelGroup = channelGroup;
        this.userChannelMap = userChannelMap;

    }


    public boolean sendMsg(String userId, String msg) {
        return sendMsg(userId, null, msg);
    }

    public boolean sendMsg(String userId, String clientType, String msg) {
        List<ChannelInfo> channelInfoList = userChannelMap.get(userId);
        if(null == channelInfoList || channelInfoList.isEmpty()){
            return false;
        }
        boolean result = false;
        for (ChannelInfo channelInfo: channelInfoList) {
            if(null == clientType || clientType.equals(channelInfo.getClientType())){
                final Channel channel = channelGroup.find(channelInfo.getChannelId());
                if(null != channel && channel.isOpen()){
//                    new TextWebSocketFrame(msg)不能在循环外实例化，否则出问题
                    channel.writeAndFlush(new TextWebSocketFrame(msg));
                    result = true;
                }

            }
        }
        return result;
    }

    public boolean broadcast(String msg) {
        channelGroup.writeAndFlush(new TextWebSocketFrame(msg));
        return true;
    }

    public boolean broadcast(String clientType, String msg) {

//        TODO 需要优化，否则在并发量和连接数大了以后可能会出问题。优化方案：每种客户端的Channel放到一个channelGroup中

        for(Map.Entry<String,List<ChannelInfo>> channelInfoList : userChannelMap.entrySet()){

            if(null == channelInfoList.getValue() || channelInfoList.getValue().isEmpty()){
                continue;
            }
            for (ChannelInfo channelInfo: channelInfoList.getValue()) {
                if(clientType.equals(channelInfo.getClientType())){
//                    new TextWebSocketFrame(msg)不能在循环外实例化，否则出问题
                    channelGroup.find(channelInfo.getChannelId()).writeAndFlush(new TextWebSocketFrame(msg));
                }
            }

        }

        return true;
    }

    public boolean close(String userId) {
        return close(userId,null );
    }

    public boolean close(String userId, String clientType) {
        List<ChannelInfo> channelInfoList = userChannelMap.get(userId);
        if(null == channelInfoList || channelInfoList.isEmpty()){
            return true;
        }
        for (ChannelInfo channelInfo: channelInfoList) {
            if(null == clientType || clientType.equals(channelInfo.getClientType())){

                close(channelInfo.getChannelId());
            }
        }
        return true;
    }

    public boolean close(ChannelId channelId) {
        if(null == channelId){
            return false;
        }
        Channel channel = channelGroup.find(channelId);
        if(null != channel){
            channel.close();
            return true;
        }
        return false;
    }
}
