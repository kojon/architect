package cn.net.chestnut.stream.consumber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
@Slf4j
public class SomeConsumer3 {

    @StreamListener(Sink.INPUT)
    public void pringMSG(Object message) {
        log.info("收到消息:{}",message);
    }
}
