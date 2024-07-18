package project.my.sbc2024.domain.model;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tb_account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * o número da conta é única.
   * Faz gerar uma constraint: garante que os
   * valores em uma coluna correspondam aos valores em outra coluna de outra
   * tabela.
   */
  @Column(unique = true)
  private String number;

  private String agency;
  /**
   * quantidade de 13 numeros, duas casas decimais
   */
  @Column(precision = 13, scale = 2)
  private BigDecimal balance;

  /**
   * 13 números com duas casas decimais
   * nome estilizado porque limit pode ser palavra reservada no banco de dados
   */
  @Column(name = "addional_limit", precision = 13, scale = 2)
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

  public String getAgency() {
    return agency;
  }

  public void setAgency(String agency) {
    this.agency = agency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public BigDecimal getLimit() {
    return limit;
  }

  public void setLimit(BigDecimal limit) {
    this.limit = limit;
  }

}
