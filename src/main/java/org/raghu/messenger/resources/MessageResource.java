package org.raghu.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.raghu.messenger.model.Message;
import org.raghu.messenger.resources.beans.MessageFilterBean;
import org.raghu.messenger.service.MessageService;

@Path("/messages")
public class MessageResource {

	MessageService messageService = new MessageService();

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Message> getMessages() {
//		return messageService.getMessages();
//	}

	// WITHOUT USING THE BEAN PARAMTER
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Message> getMessages(@QueryParam("year") int year, @QueryParam("start") int start,
//			@QueryParam("size") int size) {
//		if (year > 0)
//			return messageService.getAllMessagesForYear(year);
//		if (start >= 0 && size >= 0)
//			return messageService.getAllMessagesPaginated(start, size);
//		return messageService.getMessages();
//	}

	// USING THE BEAN PARAMTER

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@BeanParam MessageFilterBean messageFilterBean) {
		if (messageFilterBean.getYear() > 0)
			return messageService.getAllMessagesForYear(messageFilterBean.getYear());
		if (messageFilterBean.getStart() >= 0 && messageFilterBean.getSize() >= 0)
			return messageService.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
		return messageService.getMessages();
	}

	// WIHTOUT GENERATING THE RESPONSE
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Message addMessage(Message message) {
//		return messageService.addMessage(message);
//	}

	// GENERATING WITH THE ACTUAL RESPONSE
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();
	}

	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") Long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteMessage(@PathParam("messageId") Long messageId) {
		messageService.removeMessage(messageId);
	}

	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") Long messageId) {
		return messageService.getMessage(messageId);
	}

	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

}
