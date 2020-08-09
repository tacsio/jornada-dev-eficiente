package io.tacsio.coupon;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.tacsio.coupon.dto.CupomForm;
import io.tacsio.coupon.dto.CupomResponse;

@Path("/coupons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CupomController {

	@POST
	@Transactional
	public Response create(@Valid CupomForm form) {
		Cupom cupom = form.toModel();
		cupom.persist();

		return Response.ok(new CupomResponse(cupom)).build();
	}
}