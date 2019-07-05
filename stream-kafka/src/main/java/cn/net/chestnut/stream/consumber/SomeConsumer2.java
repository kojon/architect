package cn.net.chestnut.stream.consumber;

import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

// @Component
// @EnableBinding(Sink.class)
public class SomeConsumer2 {

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void pringMSG(Object message) {
        System.out.println(message);
    }
}
