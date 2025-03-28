package de.antongeiger.kafka.My.Kafka.Spring.Project.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.validation.annotation.Validated;

@Configuration
public class TopicConfig {

    @Value("${spring.kafka.topic.name}")
    private String default_topicname;

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(default_topicname)
                .partitions(3) // number of partitions
                .build();
    }

    @Bean
    public NewTopic topic_2(){
        return TopicBuilder.name("topic-one-partition")
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic topic_3(){
        return TopicBuilder.name("round-robin-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic_4(){
        return TopicBuilder.name("sticky-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic_5(){
        return TopicBuilder.name("key-hash-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic_6(){
        return TopicBuilder.name("round_robin_2")
                .partitions(2)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topic_7(){
        return TopicBuilder.name("round_robin_4")
                .partitions(4)
                .replicas(4)
                .build();
    }

    @Bean
    public NewTopic topic_8(){
        return TopicBuilder.name("round_robin_16")
                .partitions(16)
                .replicas(16)
                .build();
    }

    @Bean
    public NewTopic topic_9(){
        return TopicBuilder.name("round_robin_32")
                .partitions(32)
                .replicas(32)
                .build();
    }

}
