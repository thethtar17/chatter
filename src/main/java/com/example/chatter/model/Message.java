package com.example.chatter.model;


import jakarta.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Message {



	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "sender_id", nullable = false)
	    private User sender;

	    @ManyToOne
	    @JoinColumn(name = "receiver_id", nullable = false)
	    private User receiver;

//	    @Column(nullable = false)
//	    private String content;
	    @Column(columnDefinition = "TEXT",nullable = false)
	    private String content;
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(nullable = false, updatable = false)
	    @org.hibernate.annotations.CreationTimestamp
	    private LocalDateTime createdAt;

	    @Lob
	    @Column(name = "file_data", columnDefinition = "LONGBLOB")
	    private byte[] file;


	    @Column(name = "file_name")
	    private String fileName;
	    @Column(name = "profile_picture")
	    private String profilePicture;

	    public String getProfilePicture() {
			return profilePicture;
		}

		public void setProfilePicture(String profilePicture) {
			this.profilePicture = profilePicture;
		}
		 @Transient
		    private String senderUsername;

		    @Transient
		    private String receiverUsername;
//		 Getters and Setters
		    
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public User getSender() {
	        return sender;
	    }

	    public void setSender(User sender) {
	        this.sender = sender;
	    }

	    public User getReceiver() {
	        return receiver;
	    }

	    public void setReceiver(User receiver) {
	        this.receiver = receiver;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	 

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		
		public byte[] getFile() {
			return file;
		}

		public void setFile(byte[] file) {
			this.file = file;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		

		public String getSenderUsername() {
			return senderUsername;
		}

		public void setSenderUsername(String senderUsername) {
			this.senderUsername = senderUsername;
		}

//		public String getReceiverUsername() {
//			return receiverUsername;
//		}
//
//		public void setReceiverUsername(String receiverUsername) {
//			this.receiverUsername = receiverUsername;
//		}

		public Message() {
			
		}

		public Message(Long id, User sender, User receiver, String content, LocalDateTime createdAt, byte[] file,
				String fileName, String profilePicture) {
			super();
			this.id = id;
			this.sender = sender;
			this.receiver = receiver;
			this.content = content;
			this.createdAt = createdAt;
			this.file = file;
			this.fileName = fileName;
			this.profilePicture = profilePicture;
		}

//
//		public Message(Long id, User sender, User receiver, String content, LocalDateTime createdAt, byte[] file,
//				String fileName) {
//			super();
//			this.id = id;
//			this.sender = sender;
//			this.receiver = receiver;
//			this.content = content;
//			this.createdAt = createdAt;
//			this.file = file;
//			this.fileName = fileName;
//		}
		

		
	
    
}
