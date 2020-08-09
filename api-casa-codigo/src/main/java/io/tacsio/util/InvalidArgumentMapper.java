package io.tacsio.util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidArgumentMapper implements ExceptionMapper<IllegalArgumentException> {

	@Inject
	private MessageHelper messageHelper;

	@Override
	public Response toResponse(IllegalArgumentException exception) {
		String msg = messageHelper.getMessage(exception.getMessage());

		if(msg.trim().isEmpty()) {
			msg = "Default error!";
			exception.printStackTrace();
		}

		Map<String, String> error = new HashMap<>();
		error.put("error", msg);
		
		
		return Response
			.status(Status.BAD_REQUEST)
			.entity(error)
			.build();
	}

}