package org.oversimplify.test.impl;

import io.netty.channel.ChannelId;
import org.oversimplify.event.BusinessEvent;
import org.oversimplify.event.SocketEvent;

/**
 * @author 王晓亮
 * @date 2018/9/5 10:16
 */
public class SocketEventImpl implements SocketEvent {

    //                校验连接请求，返回用户的唯一标识。返回值不为空表示通过校验
    public String getUserIdByParameter(BusinessEvent businessEvent, String token) {
        if(null == token || token.length() == 0){
            return null;
        }
        return token;
    }

    //                连接建立前执行的事件
    public void jointBefore(BusinessEvent businessEvent,String userId, ChannelId channelId) {
        System.out.println("连接前事件，用户："+userId);
    }

    //                连接建立后执行的事件
    public void jointLater(BusinessEvent businessEvent,String userId, ChannelId channelId) {
        System.out.println("连接后事件，用户："+userId);
    }

    //                断开连接后执行的事件
    public void offLater(BusinessEvent businessEvent,String userId, ChannelId channelId) {
        System.out.println("断开后事件，用户："+userId);
    }

    //                收到消息后执行的事件
    public void receiveMessages(final BusinessEvent businessEvent, String userId, String clientType, String msg) {
        businessEvent.sendMsg(userId,"哥们儿，收到消息了。消息内容："+msg );
    }
}
