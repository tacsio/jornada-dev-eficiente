package io.tacsio.order;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.tacsio.order.dto.PedidoForm;
import io.tacsio.order.dto.PedidoResponse;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

	@POST
	@Transactional
	public Response create(@Valid PedidoForm pedidoForm) {
		Pedido pedido = pedidoForm.toModel();
		pedido.persist();

		return Response.ok(new PedidoResponse(pedido)).build();
	}

	@GET
	public List<PedidoResponse> list() {
		PanacheQuery<Pedido> pedidos = Pedido.findAll();

		return pedidos.stream()
			.map(PedidoResponse::new)
			.collect(Collectors.toList());
	}

}