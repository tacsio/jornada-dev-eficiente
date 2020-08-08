package io.tacsio.order.dto;

import javax.transaction.Transactional;
import javax.validation.constraints.Positive;

import io.tacsio.book.Livro;
import io.tacsio.book.validator.ExistsBook;
import io.tacsio.order.ItemPedido;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class ItemPedidoForm {

	@ExistsBook
	public Long livroId;

	@Positive
	public Integer quantidade;

	@Transactional
	public ItemPedido toModel() {
		Livro livro = Livro.findById(this.livroId);

		return new ItemPedido(livro, this.quantidade);
	}

}