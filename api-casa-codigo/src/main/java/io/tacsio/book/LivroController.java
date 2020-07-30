package io.tacsio.book;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.tacsio.book.dto.LivroForm;
import io.tacsio.book.dto.LivroResponse;
import io.tacsio.book.dto.LivroUnitResponse;

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

		return livros.stream().map(LivroUnitResponse::new).collect(Collectors.toList());
	}

	@GET
	@Path("/{id}")
	@Transactional
	public Response show(@PathParam("id") Long id) {
		return Livro.findByIdOptional(id)
			.map((livro) -> Response.ok(new LivroResponse((Livro)livro)))
			.orElse(Response.status(Status.NOT_FOUND))
			.build();
	}

}
