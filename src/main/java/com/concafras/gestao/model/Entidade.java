package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ENTIDADE")
@Access(AccessType.FIELD)
public class Entidade extends BaseEntidade {

  /**
   * 
   */
  private static final long serialVersionUID = -8631145176733153163L;

  public Entidade() {
    // TODO Auto-generated constructor stub
  }

  public Entidade(Integer id, String razaoSocial, String cnpj,
      String logradouro, String numero, String cidade, String estado,
      Integer idPresidente, String presidente) {
    super(id, razaoSocial, cnpj, logradouro, numero, cidade, estado,
        idPresidente, presidente);
  }
}
