package project.my.sbc2024.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tb_card")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Números do cartão são únicos,
   * Não aceitará valores duplicados para outro usuário
   */
  @Column(unique = true)
  private String number;

  /**
   * 13 números com duas casas decimais
   * nome estilizado porque limit pode ser palavra reservada no banco de dados
   */
  @Column(name = "available_limit", precision = 13, scale = 2)
  private BigDecimal limit;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public BigDecimal getLimit() {
    return limit;
  }

  public void setLimit(BigDecimal limit) {
    this.limit = limit;
  }

}
