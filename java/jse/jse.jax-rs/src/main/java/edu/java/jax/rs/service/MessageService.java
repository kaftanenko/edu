package edu.java.jax.rs.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.java.jax.rs.model.Message;

@Path("messages")
public class MessageService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage() {

		return new Message("duke42");
	}

	@POST
	// @Consumes(MediaType.APPLICATION_JSON)
	public void putMessage(Message message) {

		System.out.println(message);
	}

}
