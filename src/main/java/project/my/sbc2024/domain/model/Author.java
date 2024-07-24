package project.my.sbc2024.domain.model;

import java.util.List;

/**
 * Classes da API nativa do java:
 */
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 * Classe principal
 */
@Entity(name = "tb_author") // especificando o nome da tabela
public class Author {

  /**
   * Cada propriedade é uma coluna na tabela
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;

  /**
   * relacionamento 1 : 1
   * Chave estrangeira com conta
   * Propagação: se deletar uma conta, deleta usuário
   */
  @OneToOne(cascade = CascadeType.ALL)
  private Biography biography;

  /**
   * Relacionamento: um para muitos
   * Toda vez que eu fizer a busca para o usuário, traga todas as features
   */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Book> books;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Biography getBiography() {
    return biography;
  }

  public void setBiography(Biography biography) {
    this.biography = biography;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

}
