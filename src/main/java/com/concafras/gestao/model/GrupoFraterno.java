package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GRUPOFRATERNO")
@Access(AccessType.FIELD)
public class GrupoFraterno extends BaseEntidade {

  /**
   * 
   */
  private static final long serialVersionUID = 2640962786205948328L;

}
