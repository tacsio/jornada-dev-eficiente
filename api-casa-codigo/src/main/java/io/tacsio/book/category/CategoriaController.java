package io.tacsio.book.category;

import io.tacsio.book.category.dto.CategoriaForm;
import io.tacsio.book.category.dto.CategoriaResponse;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaController {


	@POST
	@Transactional
	public CategoriaResponse create(@Valid CategoriaForm form) {
		Categoria categoria = form.toModel();
		categoria.persist();

		return new CategoriaResponse(categoria);
	}

}
