package io.tacsio.order;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.tacsio.order.dto.PedidoForm;
import io.tacsio.order.dto.PedidoResponse;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

	@POST
	@Transactional
	public Response create(@Valid PedidoForm pedidoForm, @Context UriInfo uriInfo) {

		Pedido pedido = pedidoForm.toModel();
		pedido.persist();

		URI uri = uriInfo.getAbsolutePathBuilder().path(pedido.getId().toString()).build();
		
		return Response.created(uri).build();
	}

	@GET
	@Path("/{id}")
	@Transactional
	public Response show(@PathParam("id") Long id) {

		Optional<Pedido> pedido = Pedido.findByIdOptional(id);

		if (pedido.isEmpty())
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(new PedidoResponse(pedido.get())).build();
	}

	@GET
	public List<PedidoResponse> list() {
		PanacheQuery<Pedido> pedidos = Pedido.findAll();

		return pedidos.stream()
			.map(PedidoResponse::new)
			.collect(Collectors.toList());
	}

}