//package com.example.chatter.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//
//@Service
//public class PushNotificationService {
//
//    private final FirebaseMessaging firebaseMessaging;
//
//    @Autowired
//    public PushNotificationService(FirebaseMessaging firebaseMessaging) {
//        this.firebaseMessaging = firebaseMessaging;
//    }
//
//    public void sendNotification(String receiverToken, String title, String messageBody) {
//        Message message = Message.builder()
//                .setToken(receiverToken)
//                .putData("title", title)
//                .putData("body", messageBody)
//                .build();
//
//        try {
//            firebaseMessaging.send(message);
//        } catch (FirebaseMessagingException e) {
//            // Handle exception
//            e.printStackTrace();
//        }
//    }
//}
//
