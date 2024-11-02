package com.example.chatter.controller;



import com.example.chatter.model.Message;
import com.example.chatter.model.User;
import com.example.chatter.repository.MessageRepository;
import com.example.chatter.repository.UserRepository;
import com.example.chatter.service.ChatService;
import com.example.chatter.service.UserService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
//import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;


    @GetMapping("/history")
    public List<Message> getChatHistory(@RequestParam String senderUsername, @RequestParam String receiverUsername) {
        User sender = userService.findByUsername(senderUsername);
        User receiver = userService.findByUsername(receiverUsername);
//        logger.info("Fetched messages for receiver {}: {}", receiverUsername, senderUsername);

        return chatService.getChatHistory(sender, receiver);
    }
    @GetMapping("/inbox")
    public List<Message> getInbox( @RequestParam String senderUsername,@RequestParam String receiverUsername) {
        User receiver = userRepository.findByUsername(receiverUsername);
        User sender = userService.findByUsername(senderUsername);
//        logger.info("Fetched messages for receiver {}: {}", receiverUsername);
        return chatService.getChatHistory(receiver, sender);   
//        return chatService.getMessagesForReceiver(receiver);
        }
    
    @PostMapping("/send")
    public Message sendMessage(
        @RequestParam String senderUsername,
        @RequestParam String receiverUsername,
        @RequestParam String content,
        @RequestParam(required = false) MultipartFile file
    ) {
        User sender = userService.findByUsername(senderUsername);
        User receiver = userService.findByUsername(receiverUsername);
        return chatService.sendMessage(sender, receiver, content, file);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        messageRepository.save(message);
        return message;
    }
    
    
    @GetMapping("/download/{messageId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long messageId) {
        Message message = chatService.getMessageById(messageId);

        if (message.getFile() == null) {
            throw new RuntimeException("File not found in message");
        }

        ByteArrayResource resource = new ByteArrayResource(message.getFile());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + message.getFileName() + "\"")
                .body(resource);
    }
   
}


//package com.example.chatter.controller;
//
//import com.example.chatter.model.Message;
//import com.example.chatter.model.User;
//import com.example.chatter.repository.MessageRepository;
//import com.example.chatter.repository.UserRepository;
//import com.example.chatter.service.ChatService;
//import com.example.chatter.service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/chat")
//@CrossOrigin(origins = "http://localhost:3000")
//public class ChatController {
//    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
//
//    @Autowired
//    private ChatService chatService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    /**
//     * Get chat history between two users.
//     */
//    @GetMapping("/history")
//    public ResponseEntity<List<Message>> getChatHistory(@RequestParam String senderUsername, @RequestParam String receiverUsername) {
//        try {
////            User sender = userService.findByUsername(senderUsername);
////            User receiver = userService.findByUsername(receiverUsername);
//        	User sender = userService.findByUsername(senderUsername);
//          User receiver = userService.findByUsername(receiverUsername);
//
//            if (sender == null || receiver == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//
//            List<Message> messages = chatService.getChatHistory(sender, receiver);
//            return ResponseEntity.ok(messages);
//        } catch (Exception e) {
//            logger.error("Error fetching chat history", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    /**
//     * Get inbox messages for a receiver.
//     */
//    @GetMapping("/inbox")
//    public ResponseEntity<List<Message>> getInbox(@RequestParam String senderUsername, @RequestParam String receiverUsername) {
//        try {
//            User sender = userService.findByUsername(senderUsername);
//            User receiver = userService.findByUsername(receiverUsername);
//
//            if (sender == null || receiver == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//
//            List<Message> messages = chatService.getChatHistory(receiver, sender);
//            return ResponseEntity.ok(messages);
//        } catch (Exception e) {
//            logger.error("Error fetching inbox", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    /**
//     * Send a new message.
//     */
//    @PostMapping("/send")
//    public ResponseEntity<Message> sendMessage(
//            @RequestParam String senderUsername,
//            @RequestParam String receiverUsername,
//            @RequestParam String content,
//            @RequestParam(required = false) MultipartFile file) {
//
//        User sender = userService.findByUsername(senderUsername);
//		User receiver = userService.findByUsername(receiverUsername);
//
//		if (sender == null || receiver == null) {
//		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		}
//
//		Message message = chatService.sendMessage(sender, receiver, content, file);
//		 logger.info("senderUsername: " + senderUsername);
//		    logger.info("receiverUsername: " + receiverUsername);
//		    logger.info("content: " + content);
//		return ResponseEntity.ok(message);
//    }
//
//    /**
//     * Download a file attached to a message.
//     */
//    @GetMapping("/download/{messageId}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable Long messageId) {
//        try {
//            Message message = chatService.getMessageById(messageId);
//
//            if (message.getFile() == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//
//            ByteArrayResource resource = new ByteArrayResource(message.getFile());
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + message.getFileName() + "\"")
//                    .body(resource);
//        } catch (Exception e) {
//            logger.error("Error downloading file", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//}
//
