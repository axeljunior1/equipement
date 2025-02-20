package com.projet.equipement.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@org.springframework.stereotype.Controller
public class Controller {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        return "Réponse du serveur : " + message;
    }
}
