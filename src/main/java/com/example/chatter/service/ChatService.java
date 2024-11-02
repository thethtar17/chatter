package com.example.chatter.service;

import com.example.chatter.model.Message;
import com.example.chatter.model.User;
import com.example.chatter.repository.MessageRepository;

//import org.hibernate.boot.jaxb.JaxbLogger_.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

//    public Message sendMessage(User sender, User receiver, String content) {
//        Message message = new Message();
//        message.setSender(sender);
//        message.setReceiver(receiver);
//        message.setContent(content);
//        message.setCreatedAt(LocalDateTime.now());
//        return messageRepository.save(message);
//    }

    public List<Message> getChatHistory(User sender, User receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }
    public List<Message> getMessagesForReceiver(User receiver) {
        return messageRepository.findByReceiverOrderByCreatedAtDesc(receiver);
    }
    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
    }
    public Message sendMessage(User sender, User receiver, String content, MultipartFile file) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        if (file != null && !file.isEmpty()) {
            try {
                message.setFile(file.getBytes());
                message.setFileName(file.getOriginalFilename());
            } catch (IOException e) {
//                logger.error("Failed to attach file to message: {}", e.getMessage());
            }
        }

        return messageRepository.save(message);
    }
}