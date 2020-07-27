package io.tacsio.book;

import io.tacsio.book.dto.LivroForm;
import io.tacsio.book.dto.LivroResponse;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LivroController {

	@POST
	@Transactional
	public LivroResponse create(@Valid LivroForm livroForm) {
		Livro livro = livroForm.toModel();
		livro.persist();

		return new LivroResponse(livro);
	}
}
