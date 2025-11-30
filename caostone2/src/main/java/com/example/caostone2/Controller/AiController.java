package com.example.caostone2.Controller;

import com.example.caostone2.Service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {
    private final OpenAIService openAIService;
//13
    @PostMapping("/ask/{userId}")
    public String askFitnessCoach(@PathVariable Integer userId ,@RequestBody String message) {
        return openAIService.ask(userId, message);
    }
}