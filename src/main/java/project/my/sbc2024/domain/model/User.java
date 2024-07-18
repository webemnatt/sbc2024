package project.my.sbc2024.domain.model;

import java.util.List;

/**
 * Classes da API nativa do java:
 */
import jakarta.persistence.CascadeType;
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
@Entity(name = "tb_user") // especificando o nome da tabela
public class User {

  /**
   * Cada propriedade é uma coluna na tabela
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  /**
   * relacionamento 1 : 1
   * Chave estrangeira com conta
   * Propagação: se deletar uma conta, deleta usuário
   */
  @OneToOne(cascade = CascadeType.ALL)
  private Account account;

  @OneToOne(cascade = CascadeType.ALL)
  private Card card;

  /**
   * Relacionamento: um para muitos
   * Toda vez que eu fizer a busca para o usuário, traga todas as features
   */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Feature> features;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<News> news;

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

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public List<Feature> getFeatures() {
    return features;
  }

  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public List<News> getNews() {
    return news;
  }

  public void setNews(List<News> news) {
    this.news = news;
  }

}

