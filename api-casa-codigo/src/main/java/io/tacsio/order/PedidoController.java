package io.tacsio.order;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import io.tacsio.order.coupon.Cupom;
import io.tacsio.order.coupon.validator.ExistsCoupon;
import io.tacsio.order.dto.PedidoForm;
import io.tacsio.order.dto.PedidoResponse;
import io.tacsio.order.validator.ExistsOrder;

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

	@PUT
	@Path("/{id}/apply/{codigoCupom}")
	@Transactional
	public Response applyCoupon(@PathParam("id") @ExistsOrder Long pedidoId,
			@PathParam("codigoCupom") @ExistsCoupon String codigoCupom,
			@Context UriInfo uriInfo) {

		Cupom cupom = Cupom.find("codigo", codigoCupom).firstResult();

		Pedido pedido = Pedido.findById(pedidoId);
		pedido.aplicarCupom(cupom);
		pedido.persist();

		URI uri = uriInfo.getBaseUriBuilder()
			.path(uriInfo.getPathSegments().get(0).getPath())
			.path(pedido.getId().toString())
			.build();

		return Response.status(Status.OK).location(uri).build();
	}
}