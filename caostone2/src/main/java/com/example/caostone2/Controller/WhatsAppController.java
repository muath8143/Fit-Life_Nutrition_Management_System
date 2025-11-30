package com.example.caostone2.Controller;

import com.example.caostone2.Api.ApiResponse;
import com.example.caostone2.Service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/whatsapp")
@RequiredArgsConstructor
public class WhatsAppController {
    private final WhatsAppService whatsAppService;

    @PostMapping("/send/{phoneNumber}/{message}")
    public ResponseEntity<?> sendToWhatsApp(@PathVariable String phoneNumber,@PathVariable String message){
        whatsAppService.sendWhatsAppMessage(phoneNumber, message);
        return ResponseEntity.status(200).body(new ApiResponse("The message sent successfully"));
    }
}
