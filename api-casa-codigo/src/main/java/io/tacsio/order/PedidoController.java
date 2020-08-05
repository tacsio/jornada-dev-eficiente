package io.tacsio.order;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.tacsio.order.dto.PedidoForm;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

	@POST
	public Response create(@Valid PedidoForm pedidoForm) {

		

		return Response.ok().build();
	}
	
}