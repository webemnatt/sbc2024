package project.my.sbc2024.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.my.sbc2024.domain.model.Author;

/**
 * interface repositório do Spring Data JPA que fornece um conjunto de métodos
 * para interagir com a User Entity
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
  boolean existsByName(String name);
}
