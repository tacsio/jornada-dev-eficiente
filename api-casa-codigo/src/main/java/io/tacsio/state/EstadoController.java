package io.tacsio.state;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.tacsio.state.dto.EstadoForm;
import io.tacsio.state.dto.EstadoResponse;

@Path("/states")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoController {

	@POST
	@Transactional
	public EstadoResponse create(@Valid EstadoForm form) {
		Estado estado = form.toModel();
		estado.persist();

		return new EstadoResponse(estado);
	}

	@GET
	@Transactional
	public List<EstadoResponse> index() {

		System.out.println(Estado.findAll().count());
		Estado.findAll().stream().forEach(System.out::println);

		return Estado.findAll().stream()
			.map(estado -> new EstadoResponse((Estado) estado))
			.collect(Collectors.toList());
	}
}