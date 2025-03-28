package de.antongeiger.kafka.My.Kafka.Spring.Project.consumer;

import de.antongeiger.kafka.My.Kafka.Spring.Project.benchmarker.Benchmarker;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;


@Component
public class MessageConsumer {


    @KafkaListener( id="test_id" , topicPartitions = { @TopicPartition(topic = "my-topic" , partitions = {"1"}) } , groupId = "my-group-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

    @KafkaListener( id="2" , topicPartitions = { @TopicPartition(topic = "topic-one-partition" , partitions = {"0"}) } , groupId = "my-group-id")
    public void listen_one_partition(String message) {
        System.out.println("Received message in Topic topic-one-partition : " + message);
    }

    @KafkaListener (id="3" , topics = "round-robin-topic")
    public  void listen_round_robin(ConsumerRecord<String, String> record){
        //System.out.println("Received message in Topic topic-one-partition : " + record.value());

        Benchmarker.keys.remove(record.key());

        if (Benchmarker.keys.isEmpty()){
            System.out.println("Benchmark ended");
            Benchmarker.time_end = System.currentTimeMillis();

            System.out.println("Benchmarktime: " + String.valueOf(Benchmarker.time_end - Benchmarker.time_start));
        }

    }

    @KafkaListener (id="4" , topics = "sticky-topic")
    public  void listen_sticky(ConsumerRecord<String, String> record){
        //System.out.println("Received message in Topic topic-one-partition : " + record.value());

        Benchmarker.keys.remove(record.key());

        if (Benchmarker.keys.isEmpty()){
            System.out.println("Benchmark ended");
            Benchmarker.time_end = System.currentTimeMillis();

            System.out.println("Benchmarktime: " + String.valueOf(Benchmarker.time_end - Benchmarker.time_start));
        }

    }


    @KafkaListener (id="5" , topics = "key-hash-topic")
    public  void listen_key_hash(ConsumerRecord<String, String> record){
        //System.out.println("Received message in Topic topic-one-partition : " + record.value());

        Benchmarker.keys.remove(record.key());

        if (Benchmarker.keys.isEmpty()){
            System.out.println("Benchmark ended");
            Benchmarker.time_end = System.currentTimeMillis();

            System.out.println("Benchmarktime: " + String.valueOf(Benchmarker.time_end - Benchmarker.time_start));
        }

    }

}