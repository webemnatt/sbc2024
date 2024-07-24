package project.my.sbc2024.service;

import java.util.List;

import project.my.sbc2024.domain.model.Author;
import project.my.sbc2024.domain.model.Book;

public interface AuthorService {

  Author findById(Long id);

  List<Author> findAll();

  Author create(Author userToCreate);

  Author update(Long id, Author authorToUpdate);

  void delete(Long id);

  List<Book> findBooksByAuthor(Long id);

  Book addBookToAuthor(Long id, Book bookToAdd);

  boolean bookAlreadyExists(Long authorId, Book book);

}
