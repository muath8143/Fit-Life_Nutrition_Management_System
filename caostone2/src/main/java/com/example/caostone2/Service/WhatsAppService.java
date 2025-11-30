package com.example.caostone2.Service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    public void sendWhatsAppMessage(String phoneNumber,String message) {
        HttpResponse<String> response = Unirest.post("https://api.ultramsg.com/instance153164/messages/chat")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("token", "42xgzqm7k6lxwwfq")
                .field("to",phoneNumber)
                .field("body", message)
                .asString();
    }
}
