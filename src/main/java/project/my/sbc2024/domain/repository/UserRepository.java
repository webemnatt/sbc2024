package project.my.sbc2024.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.my.sbc2024.domain.model.User;

/**
 * interface repositório do Spring Data JPA que fornece um conjunto de métodos
 * para interagir com a User Entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Assinatura de método que permite verificar usuário já existe por número da conta
   * Entra no Account e faz um get no number
   * Faz um join numa tabela secundária.
   */
  boolean existsByAccountNumber(String accountNumber);
}

