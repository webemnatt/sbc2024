package project.my.sbc2024.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import project.my.sbc2024.domain.model.User;
import project.my.sbc2024.service.UserService;


/**
 * Classe da camada de entrada para expor a APIRest.
 * Contexto users para expor os endpoints
 */
@RestController
@RequestMapping("/users")
public class UserController {
  /**
   * Controller chamando a camada de serviços.
   * Acessa a interface com contratos, não a implementação.
   */
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Como resposta, não uma entidade ou um modelo, mas uma entidade de resposta do
   * spring:
   * O formato já diz que tipo espera entre <>
   * Expondo endpoint para o Método HTTP GET. id entre chaves porque é variável
   * Para que a id informada no path seja capturada como atributo: @PathVariable
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> findById(@PathVariable("id") Long id) {
    var user = userService.findById(id);
    return ResponseEntity.ok(user);
  }

  /**
   * Para criar usuário, precisa informar dados dentro de um corpo de requisiçãos
   * Com base nos atributos do User
   * Devolve localização do usuário criado (URL)
   * 
   * @return
   */
  @PostMapping("/")
  public ResponseEntity<User> create(@RequestBody User userToCreate) {
    var userCreated = userService.create(userToCreate);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(userCreated.getId())
        .toUri();
    return ResponseEntity.created(location).body(userCreated);
  }

}

