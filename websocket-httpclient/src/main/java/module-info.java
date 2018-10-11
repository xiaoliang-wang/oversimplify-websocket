module websocket.httpclient {

    requires websocket.threadpool;

    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires org.slf4j;

    exports org.oversimplify.httpclient;

}