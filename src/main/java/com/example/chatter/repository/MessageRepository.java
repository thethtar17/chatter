package com.example.chatter.repository;


import com.example.chatter.controller.*;
import com.example.chatter.model.Message;
import com.example.chatter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
	  List<Message> findBySenderAndReceiver(User sender, User receiver);
//    List<Message> findBySenderAndReceiverOrderByCreatedAtDesc(User sender, User receiver);
	  List<Message> findByReceiverOrderByCreatedAtDesc(User receiver);
	  List<Message> findBySenderAndReceiverOrderByCreatedAtDesc(User sender, User receiver);
//	    List<Message> findByReceiverOrderByCreatedAtDesc(User receiver);
	  
}

