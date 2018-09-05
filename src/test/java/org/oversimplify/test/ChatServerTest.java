package org.oversimplify.test;

import io.netty.channel.ChannelId;
import org.oversimplify.event.BusinessEvent;
import org.oversimplify.event.SocketEvent;
import org.oversimplify.socket.ChatServer;
import org.oversimplify.test.impl.SocketEventImpl;

/**
 * @author 王晓亮
 * @date 2018/8/30 19:02
 */
public class ChatServerTest {

    public static void main(String[] args) throws Exception {

//        实例化websocket事件处理类，该实现类可以根据业务需要自行实现
        SocketEvent socketEvent = new SocketEventImpl();
//        启动websocket服务并获取其对外暴露的接口
        final BusinessEvent businessEvent = ChatServer.start(8888, "/wxl",socketEvent);

//        通过启动方法对外暴露的BusinessEvent接口可以实现消息推送、广播、连接断开等操作。下面例子中展示了通过BusinessEvent进行消息广播，其他操作请查看BusinessEvent类的注释

//        启动线程，每隔2秒广播一条消息出去。演示通过BusinessEvent如何操作websocket连接
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    businessEvent.broadcast("websocket连接正常");
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
