package cn.net.chestnut.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomSource {
    String CHANNEL_NAME = "my-channel";

    @Output(CustomSource.CHANNEL_NAME)
    MessageChannel output();
}
