package de.antongeiger.kafka.My.Kafka.Spring.Project;


import de.antongeiger.kafka.My.Kafka.Spring.Project.config.TopicConfig;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class ManualTestBean {

    @EventListener(ApplicationReadyEvent.class)
    public void start_manual_test(){

        TopicConfig topicConfig = new TopicConfig();
        topicConfig.topic_9();
        topicConfig.topic_8();
        topicConfig.topic_7();
        topicConfig.topic_6();
    }
}
