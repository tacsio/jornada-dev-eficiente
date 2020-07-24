package io.tacsio.author;

import io.tacsio.author.dto.AutorForm;
import io.tacsio.author.dto.AutorResponse;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/authors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutorController {


	@POST
	@Transactional
	public AutorResponse create(@Valid AutorForm form) {
		Autor autor = form.toModel();
		autor.persist();

		return new AutorResponse(autor);
	}
}