package de.antongeiger.kafka.My.Kafka.Spring.Project.controller;

import de.antongeiger.kafka.My.Kafka.Spring.Project.benchmarker.Benchmarker;
import de.antongeiger.kafka.My.Kafka.Spring.Project.producer.MessageProducer;
import de.antongeiger.kafka.My.Kafka.Spring.Project.producer.WeatherProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private WeatherProducer weatherProducer;


    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        messageProducer.sendMessage("my-topic", message);
        return "Message sent: " + message;
    }


    @PostMapping("/start-test-round-robin")
    public String start_test_round_robin(@RequestParam("message") String message , @RequestParam("message_count") String message_count) {

        Integer int_message_count = Integer.valueOf(message_count);
        messageProducer.sendMessages_round_robin("round-robin-topic", message , int_message_count);
        return "Message sent: " + message;
    }

    @PostMapping("/start-test-sticky")
    public String start_test_sticky(@RequestParam("message") String message , @RequestParam("message_count") String message_count) {

        Integer int_message_count = Integer.valueOf(message_count);
        messageProducer.sendMessages_sticky("sticky-topic", message , int_message_count);
        return "Message sent: " + message;
    }


    @PostMapping("/start-test-key-hash")
    public String start_test_key_hash(@RequestParam("message") String message , @RequestParam("message_count") String message_count) {
        Integer int_message_count = Integer.valueOf(message_count);
        messageProducer.sendMessages_key_hash("key-hash-topic", message , int_message_count);
        return "Message sent: " + message;
    }

    @PostMapping("/start-weather-test-round-robin")
    public String start_weather_test_round_robin(@RequestParam("message_count") String message_count) {

        Integer int_message_count = Integer.valueOf(message_count);
        weatherProducer.sendMessages_round_robin("round-robin-topic" , int_message_count);
        return "DWD API Calls sent" ;
    }


    @PostMapping("/start-weather-test-sticky")
    public String start_weather_test_sticky(@RequestParam("message_count") String message_count) {

        Integer int_message_count = Integer.valueOf(message_count);
        weatherProducer.sendMessages_sticky("sticky-topic" , int_message_count);
        return "DWD API Calls sent";
    }


    @PostMapping("/start-weather-test-key-hash")
    public String start_weather_test_key_hash(@RequestParam("message_count") String message_count) {
        Integer int_message_count = Integer.valueOf(message_count);
        weatherProducer.sendMessages_key_hash("key-hash-topic",  int_message_count);
        return "DWD API Calls sent";
    }


    @PostMapping("/init-bm")
    public String initialize_benchmarking_partitions() {

        messageProducer.initialize_bm_partitions();
        return "Finished";
    }







}