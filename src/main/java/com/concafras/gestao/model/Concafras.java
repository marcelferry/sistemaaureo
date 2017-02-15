package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CONCAFRAS")
@Access(AccessType.FIELD)
public class Concafras extends BaseEntidade {

  /**
   * 
   */
  private static final long serialVersionUID = 1473940166581855434L;

}
