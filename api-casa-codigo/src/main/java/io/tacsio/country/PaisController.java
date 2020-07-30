package io.tacsio.country;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.tacsio.country.dto.PaisForm;
import io.tacsio.country.dto.PaisResponse;

@Path("/countries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaisController {
	
	@POST
	@Transactional
	public PaisResponse create(@Valid PaisForm form) {
		Pais pais = form.toModel();
		pais.persist();

		return new PaisResponse(pais);
	}
}