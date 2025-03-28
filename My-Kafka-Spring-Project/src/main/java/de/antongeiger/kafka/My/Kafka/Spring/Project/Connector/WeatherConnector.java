package de.antongeiger.kafka.My.Kafka.Spring.Project.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Component
public class WeatherConnector {


    public String get_current_weather(String station_id){
        String base_url =  "https://api.brightsky.dev";
        String service_url = "/current_weather";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(base_url + service_url + "?dwd_station_id=" + station_id ))
                .header("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return  response.body();
        } catch (InterruptedException | IOException e) {
            return "An Error occured";
        }

    }
}
