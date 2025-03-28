package de.antongeiger.kafka.My.Kafka.Spring.Project.producer;

import de.antongeiger.kafka.My.Kafka.Spring.Project.benchmarker.Benchmarker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.lang.Math.abs;

@Component
public class MessageProducer {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaProducer<String, String> kafkaProducer;

    public void sendMessage(String topic, String message) {
        Integer int_partition = 0;
        String key = "key";
        kafkaTemplate.send(topic , int_partition, key,   message);

    }


    public void sendMessages_round_robin(String topic, String message, int amount_messages) {
        int counter = 0;
        int number_partitions = 3; // kafkaProducer.partitionsFor(topic).size();
        int current_partition = 0;
        Benchmarker.keys = new ArrayList<>();
        Benchmarker.time_start = System.currentTimeMillis();

        do {
            current_partition = (number_partitions + counter) % number_partitions;

            kafkaTemplate.send(topic, current_partition, String.valueOf(counter), message + String.valueOf(counter));

            Benchmarker.keys.add(String.valueOf(counter));
            counter++;

        } while (counter < amount_messages);
    }


    public void sendMessages_sticky(String topic, String message, int amount_messages){
        int counter = 0;
        int number_partitions = 3; // kafkaProducer.partitionsFor(topic).size();
        int current_partition = 0;
        int partition_counter = 0;
        int sticky_batch_size = 20;

        Benchmarker.keys = new ArrayList<>();
        Benchmarker.time_start = System.currentTimeMillis();

        do {
            if (partition_counter > sticky_batch_size){
                current_partition = (current_partition + 1) % number_partitions;
                partition_counter = 0;
            }


            kafkaTemplate.send(topic,current_partition, String.valueOf(counter) , message + String.valueOf(counter));

            Benchmarker.keys.add(String.valueOf(counter));
            counter ++;
            partition_counter ++;

        }while (counter < amount_messages);

    }

    public void sendMessages_key_hash(String topic, String message, int amount_messages){
        int counter = 1;
        int number_partitions = 3; // kafkaProducer.partitionsFor(topic).size();
        int current_partition = 0;

        Benchmarker.keys = new ArrayList<>();
        Benchmarker.time_start = System.currentTimeMillis();

        do {
            current_partition = abs((String.valueOf(counter) + " Key").hashCode()) % number_partitions;
            kafkaTemplate.send(topic,current_partition, String.valueOf(counter) , message + String.valueOf(counter));
            Benchmarker.keys.add(String.valueOf(counter));
            counter ++;

        }while (counter < amount_messages);

    }


    public void initialize_bm_partitions(){
        String [] list_topic_names = {"round_robin_2" , "round_robin_4", "round_robin_16", "round_robin_32"};

        for (String topic_name : list_topic_names){
            Integer num_part = Integer.valueOf(topic_name.replace("round_robin_" , ""));

            int counter = 0;
            do {
                kafkaTemplate.send(topic_name, counter, "Key" , "Init_value");
                counter ++;
            } while (counter < num_part);


        }

    }

}