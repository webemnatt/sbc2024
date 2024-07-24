package project.my.sbc2024.service.impl;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import project.my.sbc2024.domain.model.Author;
import project.my.sbc2024.domain.model.Book;
import project.my.sbc2024.domain.repository.AuthorRepository;
import project.my.sbc2024.service.AuthorService;

/**
 * Classe camada de serviço
 */
@Service
public class AuthorServiceImpl implements AuthorService {

  /**
   * interface de acesso a dados
   */
  private final AuthorRepository authorRepository;

  /**
   * Para criar o componente precisa injetar o authorRepository via construtor
   */
  public AuthorServiceImpl(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  /**
   * Endpoint de recuperação de autor
   * Procura um autor por id.
   * Se não encontrar, lança exceção.
   */
  @Override
  public Author findById(Long id) {
    return authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  /**
   * Endpoint de cadastro de autor
   * 
   * Método para criar um autor via banco de dados
   * Faz uso do método do authorRepository para fazer a verificação do nome do
   * autor.
   * Se ele já existe, joga uma exceção.
   * 
   * O novo autor é salvo no repositório
   * 
   */
  @Override
  public Author create(Author authorToCreate) {
    if (authorRepository.existsByName(authorToCreate.getName())) {
      throw new IllegalArgumentException("This author name already exists.");
    }

    return authorRepository.save(authorToCreate);
  }

  /**
   * Traz todos os autores do banco de dados
   */
  @Override
  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  /**
   * Atualiza dados do autor (nome e biografia)
   */
  @Override
  public Author update(Long id, Author authorToUpdate) {
    Author author = findById(id);
    author.setName(authorToUpdate.getName());
    author.setBiography(authorToUpdate.getBiography());
    return authorRepository.save(author);
  }

  /**
   * Deletar um autor
   */
  @Override
  public void delete(Long id) {
    authorRepository.deleteById(id);
  }

  /**
   * Encontrar livros de um autor específico pelo ID
   */
  @Override
  public List<Book> findBooksByAuthor(Long id) {
    Author author = findById(id);
    return author.getBooks();
  }

  /**
   * Adiciona um livro a um autor existente.
   * Se o autor não for encontrado, lança status 404
   * Se o autor não tiver livros cadastrados, inicia uma array vazia.
   */
  @Override
  public Book addBookToAuthor(Long id, Book bookToAdd) {
    Author author = findById(id);
    if (author == null) {
      throw new RuntimeException("Autor não encontrado");
    }
    if (author.getBooks() == null) {
      author.setBooks(new ArrayList<>());
    }
    author.getBooks().add(bookToAdd);
    return authorRepository.save(author).getBooks().get(0);
  }

  @Override
  public boolean bookAlreadyExists(Long authorId, Book book) {
    List<Book> books = findBooksByAuthor(authorId);
    return books.stream()
        .anyMatch(
            b -> b.getTitle().equals(book.getTitle()) && b.getPublicationDate().equals(book.getPublicationDate()));
  }

}
