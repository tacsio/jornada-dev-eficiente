package io.tacsio.book;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.tacsio.book.dto.LivroForm;
import io.tacsio.book.dto.LivroResponse;
import io.tacsio.book.dto.LivroUnitResponse;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import java.util.List;
import java.util.Optional;
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
