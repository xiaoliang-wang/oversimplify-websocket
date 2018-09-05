package org.oversimplify.event;

import io.netty.channel.ChannelId;

/**
 * @author 王晓亮
 * @date 2018/8/31 18:38
 */
public interface BusinessEvent {


    /**
     * 向某用户推送消息
     * @param userId 用户唯一标识
     * @param msg 消息内容
     * @return 是否操作成功
     */
    boolean sendMsg(String userId, String msg);

    /**
     * 向某用户的某种客户端推送消息
     * @param userId 用户唯一标识
     * @param clientType 客户端类型
     * @param msg 消息内容
     * @return 是否操作成功
     */
    boolean sendMsg(String userId,String clientType, String msg);

    /**
     * 广播消息
     * @param msg 消息内容
     * @return 是否操作成功
     */
    boolean broadcast(String msg);

    /**
     * 向某种客户端广播消息
     * @param clientType 客户端类型
     * @param msg 消息内容
     * @return 是否操作成功
     */
    boolean broadcast(String clientType,String msg);

    /**
     * 断开某个用户的所有连接
     * @param userId 用户唯一标识
     * @return 是否操作成功
     */
    boolean close(String userId);

    /**
     * 断开某个用户的某种客户端的连接
     * @param userId 用户唯一标识
     * @param clientType 客户端类型
     * @return 是否操作成功
     */
    boolean close(String userId,String clientType);

    /**
     * 断开指定连接
     * @param channelId 连接通道的唯一标识
     * @return 是否操作成功
     */
    boolean close(ChannelId channelId);

}
