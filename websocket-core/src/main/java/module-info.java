/**
 * @author 王晓亮
 * @date 2018/9/28 10:29
 */
module websocket.core {
    requires netty.all;
    requires org.slf4j;

    exports org.oversimplify.websocket.start;
    exports org.oversimplify.websocket.event;
    exports org.oversimplify.websocket.enumeration;
}