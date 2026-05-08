package Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

public class AiService {
    private RestClient restClient;

    public AiService(RestClient.Builder builder, @Value("${ai.api.key}") String apiKey) {
        this.restClient = builder
                .baseUrl("https://openrouter.ai/api/v1") // Adressen till AI-tjänsten
                .defaultHeader("Authorization", "Bearer " + apiKey) // Din nyckel skickas med här
                .build();
    }
}
