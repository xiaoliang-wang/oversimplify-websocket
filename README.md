# oversimplify-websocket

#### 项目介绍
    基于netty实现websocket服务框架

    一期完成websocket框架的各种功能

    二期支持集群、多节点高可用、分区容错等，基于redis做分片记录

    三期完成基于消息消息队列完成消息推送、消息必达、已读回执等



#### 软件架构
软件架构说明


#### 使用说明

1. 本项目暂不提供web页面，建议使用第三方在线websocket测试服务进行测试。

    测试地址：http://coolaf.com/tool/chattest

    上述地址禁止了http协议，无法测试握手升级

2.启动方式：

    测试类在test包下，可以根据需要自行修改服务端口、url、websocket事件实现

3.当前测试类启动后测试连接：

    ws://localhost:8888/wxl?bbb,ios

    http://localhost:8888/wxl?bbb,ios （第三方测试页面无法测试）

    说明：{协议类型}://{host}:{port}/{url}?{token},{clientType}
