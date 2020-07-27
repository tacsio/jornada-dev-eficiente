package io.tacsio.book;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.tacsio.author.Autor;
import io.tacsio.book.dto.LivroForm;
import io.tacsio.category.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class LivroControllerTest {


	private Faker faker = new Faker();

	private RequestSpecification spec;

	Autor autor = getAutor();
	Categoria categoria = getCategoria();
	LivroForm livroForm = createForm(categoria.id, autor.id);

	@BeforeEach
	void beforeEach() {
		spec = new RequestSpecBuilder()
			.setContentType(ContentType.JSON)
			.setBasePath("/books")
			.build();
	}

	@Test
	@DisplayName("Should create a book.")
	void create() {


		given(spec)
			.body(livroForm)
			.when().post()
			.then().statusCode(200)
			.body("id", notNullValue())
			.body("titulo", is(livroForm.getTitulo()))
			.body("resumo", is(livroForm.getResumo()))
			.body("sumario", is(livroForm.getSumario()))
			.body("preco", is(livroForm.getPreco()))
			.body("isbn", is(livroForm.getIsbn()))
			.body("dataPublicacao", is(livroForm.getDataPublicacao()))
			.body("categoria.nome", is(categoria.getNome()))
			.body("autor.nome", is(autor.getNome()));
	}

	@Test
	@DisplayName("A book should have a title.")
	void validateTitle() {
		LivroForm invalid = new LivroForm(
			null,
			livroForm.getResumo(),
			livroForm.getSumario(),
			livroForm.getPreco(),
			livroForm.getPaginas(),
			livroForm.getIsbn(),
			livroForm.getDataPublicacao(),
			livroForm.getIdCategoria(),
			livroForm.getIdAutor());

		given(spec)
			.body(invalid)
			.when().post()
			.then().statusCode(400);
	}

	@Test
	@DisplayName("The book's name should be unique.")
	void validateUniqueTitle() {
		//TODO:
	}

	@Test
	@DisplayName("A book's abstract is required.")
	void validateAbstract() {
		LivroForm invalid = new LivroForm(
			livroForm.getTitulo(),
			null,
			livroForm.getSumario(),
			livroForm.getPreco(),
			livroForm.getPaginas(),
			livroForm.getIsbn(),
			livroForm.getDataPublicacao(),
			livroForm.getIdCategoria(),
			livroForm.getIdAutor());

		given(spec)
			.body(invalid)
			.when().post()
			.then().statusCode(400);
	}

	@Test
	@DisplayName("A book must have a price of at least 20.")
	void validateBookMinimalPrice() {
		LivroForm invalid = new LivroForm(
			livroForm.getTitulo(),
			livroForm.getResumo(),
			livroForm.getSumario(),
			19.99,
			livroForm.getPaginas(),
			livroForm.getIsbn(),
			livroForm.getDataPublicacao(),
			livroForm.getIdCategoria(),
			livroForm.getIdAutor());

		given(spec)
			.body(invalid)
			.when().post()
			.then().statusCode(400);
	}

	@Test
	@DisplayName("A book must have page count of at least 100.")
	void validateBookMinimalPageCount() {
		LivroForm invalid = new LivroForm(
			livroForm.getTitulo(),
			livroForm.getResumo(),
			livroForm.getSumario(),
			livroForm.getPreco(),
			99,
			livroForm.getIsbn(),
			livroForm.getDataPublicacao(),
			livroForm.getIdCategoria(),
			livroForm.getIdAutor());

		given(spec)
			.body(invalid)
			.when().post()
			.then().statusCode(400);
	}

	@Test
	@DisplayName("The book's ISBN is mandatory.")
	void validateISBN() {
		LivroForm invalid = new LivroForm(
			livroForm.getTitulo(),
			livroForm.getResumo(),
			livroForm.getSumario(),
			livroForm.getPreco(),
			livroForm.getPaginas(),
			null,
			livroForm.getDataPublicacao(),
			livroForm.getIdCategoria(),
			livroForm.getIdAutor());

		given(spec)
			.body(invalid)
			.when().post()
			.then().statusCode(400);
	}

	@Test
	@DisplayName("The book's ISBN is unique.")
	void validateUniqueISBN() {
		//TODO:
	}

	@Test
	@DisplayName("The book's release date should be in the future.")
	void validateReleaseDate() {
		LivroForm invalid = new LivroForm(
			livroForm.getTitulo(),
			livroForm.getResumo(),
			livroForm.getSumario(),
			livroForm.getPreco(),
			livroForm.getPaginas(),
			livroForm.getIsbn(),
			LocalDate.now().minusDays(1),
			livroForm.getIdCategoria(),
			livroForm.getIdAutor());

		given(spec)
			.body(invalid)
			.when().post()
			.then().statusCode(400);
	}

	@Test
	@DisplayName("The book's category is mandatory.")
	void validateCategory() {
		LivroForm invalid = new LivroForm(
			livroForm.getTitulo(),
			livroForm.getResumo(),
			livroForm.getSumario(),
			livroForm.getPreco(),
			livroForm.getPaginas(),
			livroForm.getIsbn(),
			livroForm.getDataPublicacao(),
			null,
			livroForm.getIdAutor());

		given(spec)
			.body(invalid)
			.when().post()
			.then().statusCode(400);
	}

	@Test
	@DisplayName("The book's author is mandatory.")
	void validateAuthor() {
		LivroForm invalid = new LivroForm(
			livroForm.getTitulo(),
			livroForm.getResumo(),
			livroForm.getSumario(),
			livroForm.getPreco(),
			livroForm.getPaginas(),
			livroForm.getIsbn(),
			livroForm.getDataPublicacao(),
			livroForm.getIdCategoria(),
			null);

		given(spec)
			.body(invalid)
			.when().post()
			.then().statusCode(400);
	}

	@Transactional
	private Autor getAutor() {
		String nome = "Eiji Yoshikawa";
		String email = "email@mail.com";
		String descricao = "Eiji Yoshikawa foi um autor de romances históricos japonês.";

		Autor autor = new Autor(nome, email, descricao);
		autor.persist();

		return autor;
	}

	@Transactional
	private Categoria getCategoria() {
		String nome = "Epic";

		Categoria categoria = new Categoria(nome);
		categoria.persist();

		return categoria;
	}


	/**
	 * Creates a book from data;
	 * <ul>
	 *   <li>titulo</li>
	 *   <li>resumo</li>
	 *   <li>sumario</li>
	 *   <li>preco</li>
	 *   <li>isbn</li>
	 *   <li>dataPublicacao</li>
	 *   <li>idCategoria</li>
	 *   <li>idAutor</li>
	 * </ul>
	 *
	 * @param idCategoria
	 * @param idAutor
	 * @return
	 */
	private LivroForm createForm(long idCategoria, long idAutor) {
		String titulo = "Musashi";
		String resumo = "Este romance épico baseado na história japonesa narra um período da vida de Musashi, samurai japonês que viveu presumivelmente entre 1584 e 1645. A história começa com Musashi recuperando os sentidos em meio a pilhas de cadáveres do lado dos vencidos na batalha de Sekigahara, em 1600. Perambula a seguir em meio a um Japão em crise, onde samurais, condenados por senhores feudais ao desemprego e à miséria (os rounin), semeiam a vilania ditando a lei do mais forte. Musashi será mais um dentre estes pequenos tiranos, derrotando impiedosamente quem encontra pela frente até que um monge armado apenas de sua malícia e de alguns preceitos filosóficos zen-budistas consegue capturá-lo e pô-lo rudemente à prova. Musashi foge graças a uma jovem admiradora, para ser novamente capturado para ficar três anos confinado em uma masmorra, onde uma penitência feita de leituras e reflexões o fará ver um novo sentido para a vida, assim como novos usos para sua força e habilidade descomunais. Os caminhos rumo à plenitude do ser jamais são fáceis, e em seus anos de peregrinação em busca da perfeição tanto espiritual quanto guerreira enfrentará os mais diversos adversários. É numa dessas situações que, totalmente acuado, usará pela primeira vez, em meio ao calor da luta e quase inconscientemente de início, a técnica das duas espadas, o estilo Niten ichi, que o tornaria famoso pelo resto dos tempos. O leitor poderá assistir a seu amadurecimento, acompanhando o percurso que o levou a transformar-se de garoto selvagem e sanguinário no maior e mais sábio dos samurais, capaz de entender e amar tanto a esgrima quanto as artes.";
		String sumario = "# The Little Bell\n" +
			"# The Comb\n" +
			"# The Flower Festival\n" +
			"# The Dowager's Wrath\n" +
			"# The Art of War\n" +
			"# The Old Cryptomeria Tree\n" +
			"# The Rock and the Tree\n" +
			"# The Birth of Musashi\n" +
			"# The Yoshioka School\n" +
			"# The Wheel of Fortune\n" +
			"# Encounter and Retreat\n" +
			"# The Water Sprite\n" +
			"# A Spring Breeze\n" +
			"# The Hōzōin\n" +
			"# Hannya Plain\n" +
			"# The Koyagyū Fief\n" +
			"# The Peony\n" +
			"# Jōtarō's Revenge\n" +
			"# The Nightingales\n" +
			"# Sasaki Kojirō\n" +
			"# Reunion in Osaka\n" +
			"# The Handsome Young Man\n" +
			"# The Seashell of Forgetfulness\n" +
			"# A Hero's Passing\n" +
			"# The Drying Pole\n" +
			"# Eagle Mountain\n" +
			"# The Mayfly in Winter\n" +
			"# The Pinwheel\n" +
			"# The Flying Horse\n" +
			"# The Butterfly in Winter\n" +
			"# The Announcement\n" +
			"# The Great Bridge at Gojō Avenue\n" +
			"# The Withered Field\n" +
			"# A Man of Parts\n" +
			"# Too Many Kojirōs\n" +
			"# The Younger Brother\n" +
			"# A Mother's Love\n" +
			"# The Urbane Craftsman\n" +
			"# Reverberations in the Snow\n" +
			"# The Elegant People\n" +
			"# The Broken Lute\n" +
			"# A Sickness of the Heart\n" +
			"# The Scent of Aloeswood\n" +
			"# The Gate\n" +
			"# A Toast to the Morrow\n" +
			"# The Death Trap\n" +
			"# A Meeting in the Moonlight\n" +
			"# Stray Geese\n" +
			"# The Spreading Pine\n" +
			"# An Offering for the Dead\n" +
			"# A Drink of Milk\n" +
			"# Entwining Branches\n" +
			"# The Male and Female Waterfalls";
		double preco = 83.50;
		int paginas = 924;
		String isbn = "857448007X";

		LocalDate dataPublicacao = LocalDate.now().plusDays(1);

		return new LivroForm(titulo, resumo, sumario, preco, paginas, isbn, dataPublicacao, idCategoria, idAutor);
	}
}