package cn.net.chestnut.stream.producer;

import cn.net.chestnut.stream.source.CustomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

// 生产者类
@Component
// 绑定两个管道
@EnableBinding({Source.class, CustomSource.class})
public class SomeProducer {
    @Autowired
    @Qualifier(Source.OUTPUT)  // 指定channel Bean的名称
    private MessageChannel channel;

    @Autowired
    @Qualifier(CustomSource.CHANNEL_NAME)  // 指定channel Bean的名称
    private MessageChannel customChannel;

    public void sendMsg(String message) {
        // 通过消息管道发送信息
        channel.send(MessageBuilder.withPayload(message).build());
        customChannel.send(MessageBuilder.withPayload(message).build());
    }
}


