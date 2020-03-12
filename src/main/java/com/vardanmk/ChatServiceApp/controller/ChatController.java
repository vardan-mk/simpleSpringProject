package com.vardanmk.ChatServiceApp.controller;

import com.vardanmk.ChatServiceApp.chat.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.lang.String.format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/chatservice/*")
    public String chatService() {
        return "chatservice";
    }

    @MessageMapping("/chat/{roomId}/chat_send")
    public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend(format("/topic/%s", roomId), chatMessage);
        log.info("in room with name " + roomId + " the user with name " + chatMessage.getSender() + " send message");
    }

    @MessageMapping("/chat/{roomId}/chat_register")
    public void register(@DestinationVariable String roomId, @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSend(format("/topic/%s", roomId), chatMessage);
        log.info("in room with name " + roomId + " the user with name " + chatMessage.getSender() + " join to chat");
    }

    @MessageMapping("chat/{roomId}/leave")
    public void leaveChat(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
        ChatMessage leaveMessage = new ChatMessage();
        leaveMessage.setType(ChatMessage.MessageType.LEAVE);
        leaveMessage.setSender(chatMessage.getSender());
        messagingTemplate.convertAndSend(format("/topic/%s", roomId), leaveMessage);
        log.info("in room with name " + roomId + " the user with name " + chatMessage.getSender() + " leave the chat");
    }

}
