package de.antongeiger.kafka.My.Kafka.Spring.Project.producer;

import de.antongeiger.kafka.My.Kafka.Spring.Project.Connector.WeatherConnector;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class WeatherProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaProducer<String, String> kafkaProducer;
    @Autowired
    private WeatherConnector weatherConnector;


    public void sendMessage(String message) {
        String topic = "topic-one-partition";
        Integer int_partition = 0;
        String key = "key";
        kafkaTemplate.send(topic , int_partition, key,   message);
    }



    public void sendMessages_round_robin(String topic , int iterations_max) {
        String [] stations = new String[]{"01766", "02932", "01262", "04336"};
        int counter = 0;
        int number_partitions = 3; // kafkaProducer.partitionsFor(topic).size();
        int current_partition = 0;
        int iteration_limiter = 0;

        do {
            for (String station_id : stations) {
                current_partition = (number_partitions + counter) % number_partitions;

                String message = weatherConnector.get_current_weather(station_id);

                kafkaTemplate.send(topic, current_partition, station_id, message);
                counter++;
            }
            iteration_limiter ++;
        } while (iteration_limiter < iterations_max);
    }


    public void sendMessages_sticky(String topic,  int iterations_max){
        String [] stations = new String[]{"01766", "02932", "01262", "04336"};
        int counter = 0;
        int number_partitions = 3; // kafkaProducer.partitionsFor(topic).size();
        int current_partition = 0;
        int partition_counter = 0;
        int sticky_batch_size = 3;
        int iterations_counter = 0;
        do {
            for (String station_id : stations){
                if (partition_counter >= sticky_batch_size){
                    current_partition = (current_partition + 1) % number_partitions;
                    partition_counter = 0;
                }
                counter ++;
                partition_counter ++;

                String message = weatherConnector.get_current_weather(station_id);

                kafkaTemplate.send(topic,current_partition, station_id , message);
            }
            iterations_counter ++;

        }while (iterations_counter < iterations_max);

    }

    public void sendMessages_key_hash(String topic, int iterations_max){
        String [] stations = new String[]{"01766", "02932", "01262", "04336"};
        int iteration_limiter = 0;
        int number_partitions = 3; // kafkaProducer.partitionsFor(topic).size();
        int current_partition = 0;

        do {

            for (String station_id : stations){
                current_partition = station_id.hashCode() % number_partitions;
                String message = weatherConnector.get_current_weather(station_id);
                kafkaTemplate.send(topic,current_partition, station_id , message);
            }

            iteration_limiter ++;

        }while (iteration_limiter < iterations_max);

    }


}