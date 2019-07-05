package cn.net.chestnut.stream.consumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;

import javax.annotation.PostConstruct;

// @Component
// @EnableBinding(Sink.class)
public class SomeConsumer {
    @Autowired
    @Qualifier(Sink.INPUT)  // 指定channel Bean的名称
    private SubscribableChannel channel;

    @PostConstruct
    public void printMSG() {
        channel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println(message.getHeaders() + " , ");
                byte[] payload = (byte[])message.getPayload();
                System.out.println(new String(payload));
            }
        });
    }
}
