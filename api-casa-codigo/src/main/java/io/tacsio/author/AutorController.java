package io.tacsio.author;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.tacsio.author.dto.AutorForm;
import io.tacsio.author.dto.AutorResponse;

@Path("/authors")
public class AutorController {

	@POST
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AutorResponse create(@Valid AutorForm form) {
		Autor autor = form.toModel();
		autor.persist();

		return new AutorResponse(autor);
	}
}