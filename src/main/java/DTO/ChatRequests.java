package DTO;

import java.util.List;

public record ChatRequests(List<Choice> choices) {
    public record Choice(String message, String finish_reason) {
    }
}
