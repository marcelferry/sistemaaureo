package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("POSTO")
@Access(AccessType.FIELD)
public class Posto extends BaseEntidade {

  /**
   * Generated SerialVerionUID
   */
  private static final long serialVersionUID = -7755501905311982278L;

}
