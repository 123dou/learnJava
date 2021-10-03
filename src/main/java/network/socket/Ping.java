package network.socket;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class Ping {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                .timeout(Duration.ofSeconds(2))
                .GET()
                .build();
        HttpResponse<String> resp = client
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        System.out.println(resp.body());
    }
}
