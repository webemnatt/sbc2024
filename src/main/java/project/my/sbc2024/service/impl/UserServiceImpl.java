package project.my.sbc2024.service.impl;


import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import project.my.sbc2024.domain.model.User;
import project.my.sbc2024.domain.repository.UserRepository;
import project.my.sbc2024.service.UserService;

/**
 * Classe camada de serviço
 */
@Service
public class UserServiceImpl implements UserService {

  /**
   * interface de acesso a dados
   */
  private final UserRepository userRepository;

  /**
   * Para criar o componente precisa injetar o UserRepository via construtor
   */
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Endpoint de recuperação de usuário
   * Procura um usuário por id.
   * Se não encontrar, lança exceção.
   */
  @Override
  public User findById(Long id) {
    return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  /**
   * Endpoint de cadastro de usuário
   * Método para criar um usuário via banco de dados
   * Faz uso do método do UserRepository para fazer a verificação do número de
   * conta.
   * 
   */
  @Override
  public User create(User userToCreate) {
    /**
     * Se a id do usuário ou o usuário em si já existir no repositório de usuários,
     * joga uma exceção para impedir cadastro dubplicado de usuário
     */
    // if (userToCreate.getId() != null &&
    // userRepository.existsById(userToCreate.getId())) {
    // throw new IllegalArgumentException("This user id already exists.");
    // }

    if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
      throw new IllegalArgumentException("This account number already exists.");
    }
    /**
     * Como está criando um novo usuário, precisa ser salvo no repositório
     */
    return userRepository.save(userToCreate);
  }

}
