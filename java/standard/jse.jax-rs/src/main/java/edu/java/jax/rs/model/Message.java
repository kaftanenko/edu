package edu.java.jax.rs.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType
public class Message {
	
	private String content;

	public Message(String content) {
		
		this.content=content;
	}
	
}
