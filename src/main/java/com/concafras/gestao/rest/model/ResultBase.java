package com.concafras.gestao.rest.model;

public class ResultBase {
  private Boolean success;
  private String error;
  private Integer numberError;

  public ResultBase() {
  }

  public ResultBase(ResultBase resultBase) {
    this.success = resultBase.success;
    this.error = resultBase.error;
    this.numberError = resultBase.numberError;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public Integer getNumberError() {
    return numberError;
  }

  public void setNumberError(Integer numberError) {
    this.numberError = numberError;
  }
}