package io.tacsio.book;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.tacsio.book.dto.LivroForm;
import io.tacsio.book.dto.LivroResponse;
import io.tacsio.book.dto.LivroUnitResponse;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

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

	@GET
	public List<LivroUnitResponse> list() {
		PanacheQuery<Livro> livros = Livro.findAll();
		
		return livros
			.stream()
			.map(LivroUnitResponse::new)
			.collect(Collectors.toList());
	}
}
