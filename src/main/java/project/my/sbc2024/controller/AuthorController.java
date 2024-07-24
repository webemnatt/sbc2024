package project.my.sbc2024.controller;

import java.net.URI;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import project.my.sbc2024.domain.model.Author;
import project.my.sbc2024.domain.model.Book;
import project.my.sbc2024.domain.repository.AuthorRepository;
import project.my.sbc2024.service.AuthorService;

/**
 * Classe da camada de entrada para expor a APIRest.
 * Contexto authors para expor os endpoints
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {
  /**
   * Controller chamando a camada de serviços.
   * Acessa a interface com contratos, não a implementação.
   */
  private final AuthorService authorService;

  @Autowired
  private AuthorRepository authorRepository;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  /**
   * Como resposta, não uma entidade ou um modelo, mas uma entidade de resposta do
   * spring:
   * O formato já diz que tipo espera entre <>
   * Expondo endpoint para o Método HTTP GET. id entre chaves porque é variável
   * Para que a id informada no path seja capturada como atributo: @PathVariable
   */
  @GetMapping("/{id}")
  public ResponseEntity<Author> findById(@PathVariable("id") Long id) {
    var user = authorService.findById(id);
    return ResponseEntity.ok(user);
  }

  /**
   * Para criar autor, precisa informar dados dentro de um corpo de requisiçãos
   * Com base nos atributos do Author
   * Devolve localização do autor criado (URL)
   * Permite passar apenas o nome, nome e biografia, ou nome, biografia e livros.
   * 
   * @return
   */
  @PostMapping("/")
  public ResponseEntity<?> create(@RequestBody Author authorToCreate) {
    try {
      if (authorToCreate.getName() == null || authorToCreate.getName().trim().isEmpty()) {
        throw new BadRequestException("Author name is required");
      }
      var authorCreated = authorService.create(authorToCreate);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(authorCreated.getId())
          .toUri();
      return ResponseEntity.created(location).body(authorCreated);
    } catch (BadRequestException e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", "Bad Request");
      errorResponse.put("message", e.getMessage());
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<Author>> findAll() {
    var authors = authorService.findAll();
    return ResponseEntity.ok(authors);
  }

  /**
   * Atualiza o campo nome se somente ele for informado.
   * Atualiza o campo biografia se somente ele for informado.
   * Atualiza e salva o autor no repositório.
   * 
   * @param id
   * @param authorToUpdate
   * @return status 200
   */
  @PutMapping("/{id}")
  public ResponseEntity<Author> update(@PathVariable("id") Long id, @RequestBody Author authorToUpdate) {
    Author author = authorRepository.findById(id).orElseThrow();

    if (authorToUpdate.getName() != null && !authorToUpdate.getName().isEmpty()) {
      author.setName(authorToUpdate.getName());
    }

    if (authorToUpdate.getBiography() != null && authorToUpdate.getBiography().getText() != null) {
      author.getBiography().setText(authorToUpdate.getBiography().getText());
    }

    authorRepository.save(author);

    return ResponseEntity.ok(author);
  }

  /**
   * Deleta autor do banco de dados
   * 
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    authorService.delete(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Retorna uma lista dos livros de um autor via ID.
   * Se informa id de autor inexistente: retorna 404
   * 
   * @param id
   * @return
   */
  @GetMapping("/{id}/books")
  public ResponseEntity<List<Book>> findBooksByAuthor(@PathVariable("id") Long id) {
    var books = authorService.findBooksByAuthor(id);
    return ResponseEntity.ok(books);
  }

  /**
   * Adiciona um livro para um autor específico e existente.
   * Se tenta cadastrar livro em id de autor inexistente ou livro repetido:
   * retorna 404
   * 
   * @param id
   * @param bookToAdd
   * @return
   */
  @PostMapping("/{id}/books")
  public ResponseEntity<Book> addBookToAuthor(@PathVariable("id") Long id, @RequestBody Book bookToAdd) {
    if (authorService.bookAlreadyExists(id, bookToAdd)) {
      return ResponseEntity.badRequest().build();
    }
    var bookAdded = authorService.addBookToAuthor(id, bookToAdd);
    return ResponseEntity.ok(bookAdded);
  }

}
